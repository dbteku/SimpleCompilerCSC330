package com.teamhandsome.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;
import com.teamhandsome.compiler.models.SyntaxTree;
import com.teamhandsome.compiler.models.Token;
import com.teamhandsome.compiler.syntax.rules.BinaryCompToBinaryCompStatement;
import com.teamhandsome.compiler.syntax.rules.BodyToForLoop;
import com.teamhandsome.compiler.syntax.rules.Else;
import com.teamhandsome.compiler.syntax.rules.EmptyParams;
import com.teamhandsome.compiler.syntax.rules.ForLoopStatement;
import com.teamhandsome.compiler.syntax.rules.FunctionToNestedFunction;
import com.teamhandsome.compiler.syntax.rules.FunctionToProgram;
import com.teamhandsome.compiler.syntax.rules.If;
import com.teamhandsome.compiler.syntax.rules.IfElse;
import com.teamhandsome.compiler.syntax.rules.IfStatement;
import com.teamhandsome.compiler.syntax.rules.IncrementorToModifiedAssignment;
import com.teamhandsome.compiler.syntax.rules.MathOpToNestedMathOp;
import com.teamhandsome.compiler.syntax.rules.MathOpToVariable;
import com.teamhandsome.compiler.syntax.rules.MathOpVariableName;
import com.teamhandsome.compiler.syntax.rules.MathOperation;
import com.teamhandsome.compiler.syntax.rules.MathOperationParam;
import com.teamhandsome.compiler.syntax.rules.MathOperationStatement;
import com.teamhandsome.compiler.syntax.rules.MathOperationToBinaryComparison;
import com.teamhandsome.compiler.syntax.rules.MathOperatorToMathOperation;
import com.teamhandsome.compiler.syntax.rules.MathVariable;
import com.teamhandsome.compiler.syntax.rules.MethodCall;
import com.teamhandsome.compiler.syntax.rules.MethodCallToMathOperation;
import com.teamhandsome.compiler.syntax.rules.ModAssignment;
import com.teamhandsome.compiler.syntax.rules.ModifiedAssignmentStatement;
import com.teamhandsome.compiler.syntax.rules.MultiDeclareVariable;
import com.teamhandsome.compiler.syntax.rules.MultiDeclareVariableName;
import com.teamhandsome.compiler.syntax.rules.NameParToMath;
import com.teamhandsome.compiler.syntax.rules.NameToMathOperation;
import com.teamhandsome.compiler.syntax.rules.ReturnStatement;
import com.teamhandsome.compiler.syntax.rules.StatementToBody;
import com.teamhandsome.compiler.syntax.rules.StatementToNestedStatement;
import com.teamhandsome.compiler.syntax.rules.TypeToTypeArray;
import com.teamhandsome.compiler.syntax.rules.TypeVariable;
import com.teamhandsome.compiler.syntax.rules.TypedMathOperationBodyToFunction;
import com.teamhandsome.compiler.syntax.rules.TypedVariableName;
import com.teamhandsome.compiler.syntax.rules.ValueToMathOp;
import com.teamhandsome.compiler.syntax.rules.VariableNameStatement;
import com.teamhandsome.compiler.syntax.rules.VariableParam;
import com.teamhandsome.compiler.syntax.rules.VariableStatement;
import com.teamhandsome.compiler.syntax.rules.WhileLoop;
import com.teamhandsome.compiler.syntax.rules.WhileLoopStatement;
import com.teamhandsome.exceptions.InvalidSyntaxException;
import com.teamhandsome.grammar.KeyWord;
import com.teamhandsome.interfaces.IGrammar;
import com.teamhandsome.interfaces.IRule;

public class SyntaxParser {

	private final List<IGrammar> TYPES;
	private final List<IGrammar> KEYWORDS;
	private final List<IGrammar> OPERATORS;
	private final List<IGrammar> MODIFIERS;
	private final List<IGrammar> COMPARATORS;
	private final List<IRule> RULES;

	public SyntaxParser(){
		TYPES = Arrays.asList(new IGrammar[]{ new KeyWord("int"), new KeyWord("double"), new KeyWord("void"), new KeyWord("String")});
		KEYWORDS = Arrays.asList(new IGrammar[]{new KeyWord("while"), new KeyWord("for"), new KeyWord("if"), new KeyWord("else"), new KeyWord("return")});
		OPERATORS = Arrays.asList(new IGrammar[]{new KeyWord("+"), new KeyWord("-"), new KeyWord("*"), new KeyWord("/")});
		MODIFIERS = Arrays.asList(new IGrammar[]{new KeyWord("++"), new KeyWord("--"), new KeyWord("+="), new KeyWord("-=")});
		COMPARATORS = Arrays.asList(new IGrammar[]{new KeyWord("=="), new KeyWord("<="), new KeyWord(">="), new KeyWord("!="), new KeyWord(">"), new KeyWord("<")});
		RULES = Arrays.asList(new IRule[]{new FunctionToNestedFunction(), new FunctionToProgram(), 
				new TypedMathOperationBodyToFunction(), new VariableParam(), new EmptyParams(),
				new MathOperationParam(), new MethodCall(), new MethodCallToMathOperation(), new IncrementorToModifiedAssignment(),
				new MathOperationToBinaryComparison(), new StatementToBody(), new StatementToNestedStatement(),
				new VariableStatement(), new VariableNameStatement(), new ForLoopStatement(), new ReturnStatement(),
				new WhileLoopStatement(), new IfStatement(), new MathOperationStatement(), new ModifiedAssignmentStatement(),
				new MultiDeclareVariable(), new TypeVariable(), new MathVariable(), new TypedVariableName(),
				new MultiDeclareVariableName(), new MathOpVariableName(), new BodyToForLoop(), new If(), new IfElse(),
				new WhileLoop(), new NameParToMath(), new ValueToMathOp(), new MathOperation(), new MathOpToNestedMathOp(),
				new NameToMathOperation(), new TypeToTypeArray(), new MathOpToVariable(), new BinaryCompToBinaryCompStatement(),
				new ModAssignment(), new Else() , new MathOperatorToMathOperation()});
	}

	public SyntaxTree toTree(List<Token> tokens) throws InvalidSyntaxException{
		SyntaxTree tree = new SyntaxTree();
		List<SyntaxNode> nodes = new ArrayList<>();
		SyntaxNode currentNode = new SyntaxNode(NodeType.NULL);
		SyntaxNode nextNode = new SyntaxNode(NodeType.NULL);
		for (int i = 0; i < tokens.size(); i++) {
			currentNode = convertNode(tokens.get(i));
			nextNode = new SyntaxNode(NodeType.NULL);
			if(i < tokens.size() - 1){
				nextNode = convertNode(tokens.get(i + 1));
			}
			nodes.add(currentNode);
			reduce(nodes, nextNode);
		}
		if(nodes.size() == 1){
			tree = new SyntaxTree(nodes.get(0));
		}else{
			throw new InvalidSyntaxException();
		}
		return tree;
	}

	private SyntaxNode convertNode(Token token){
		SyntaxNode node = new SyntaxNode(NodeType.NULL);
		switch(token.getType()){
		case NAME:
			node = convertNameToken(token);
			break;
		case NUMBER:
			node = new SyntaxNode(NodeType.VALUE, token);
			break;
		case STRING:
			node = new SyntaxNode(NodeType.VALUE, token);
			break;
		case SYMBOL:
			node = convertSymbol(token);
			break;
		default:
			break;
		}
		return node;
	}

	private SyntaxNode convertNameToken(Token token){
		SyntaxNode node = new SyntaxNode(NodeType.NULL);
		if(isType(token.getValue())){
			node = new SyntaxNode(NodeType.TYPE, token);
		}
		else if(isKeyWord(token.getValue())){
			node = new SyntaxNode(NodeType.KEYWORD, token);
		}else{
			node = new SyntaxNode(NodeType.NAME, token);
		}
		return node;
	}

	private SyntaxNode convertSymbol(Token token){
		SyntaxNode node = new SyntaxNode(NodeType.NULL);
		if(isOperator(token.getValue())){
			node = new SyntaxNode(NodeType.OPERATOR, token);
		}
		else if(token.getValue().equalsIgnoreCase("=")){
			node = new SyntaxNode(NodeType.EQUALS, token);
		}
		else if (token.getValue().equals(";")){
			node = new SyntaxNode(NodeType.SEMICOLON, token);
		}
		else if (isModifiedAssignment(token.getValue())){
			node = new SyntaxNode(NodeType.INCREMENTOR, token);
		}
		else if (isComparator(token.getValue())){
			node = new SyntaxNode(NodeType.COMPARATOR, token);
		}
		else{
			node = new SyntaxNode(NodeType.TOKEN, token);
		}
		return node;
	}

	private boolean isType(String value){
		boolean isGrammar = false;
		for (int i = 0; i < TYPES.size(); i++) {
			IGrammar word = TYPES.get(i);
			isGrammar = word.isGrammar(value);
			if(isGrammar){
				i = TYPES.size();
			}
		}
		return isGrammar;
	}

	private boolean isKeyWord(String value){
		boolean isGrammar = false;
		for (int i = 0; i < KEYWORDS.size(); i++) {
			IGrammar word = KEYWORDS.get(i);
			isGrammar = word.isGrammar(value);
			if(isGrammar){
				i = KEYWORDS.size();
			}
		}
		return isGrammar;
	}

	private boolean isOperator(String value){
		boolean isGrammar = false;
		for (int i = 0; i < OPERATORS.size(); i++) {
			IGrammar word = OPERATORS.get(i);
			isGrammar = word.isGrammar(value);
			if(isGrammar){
				i = OPERATORS.size();
			}
		}
		return isGrammar;
	}

	private boolean isComparator(String value) {
		boolean isGrammar = false;
		for (int i = 0; i < COMPARATORS.size(); i++) {
			IGrammar word = COMPARATORS.get(i);
			isGrammar = word.isGrammar(value);
			if(isGrammar){
				i = COMPARATORS.size();
			}
		}
		return isGrammar;
	}

	private boolean isModifiedAssignment(String value) {
		boolean isGrammar = false;
		for (int i = 0; i < MODIFIERS.size(); i++) {
			IGrammar word = MODIFIERS.get(i);
			isGrammar = word.isGrammar(value);
			if(isGrammar){
				i = MODIFIERS.size();
			}
		}
		return isGrammar;
	}

	private void reduce(List<SyntaxNode> nodes, SyntaxNode nextNode) {
		boolean reduce = true;
		while(reduce){
			reduce = false;
			for (int i = 0; i < RULES.size(); i++) {
				IRule rule = RULES.get(i);
				if(rule.isRule(nodes, nextNode)){
					reduce = true;
					SyntaxNode node = rule.applyRule(nodes);
					nodes.add(node);
					i = RULES.size();
				}
			}
		}
	}
}
