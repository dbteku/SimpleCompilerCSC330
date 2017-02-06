package com.teamhandsome.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;
import com.teamhandsome.compiler.models.SyntaxTree;
import com.teamhandsome.compiler.models.Token;
import com.teamhandsome.compiler.syntax.rules.BodyToForLoop;
import com.teamhandsome.compiler.syntax.rules.EmptyParams;
import com.teamhandsome.compiler.syntax.rules.ForLoopStatement;
import com.teamhandsome.compiler.syntax.rules.FunctionToNestedFunction;
import com.teamhandsome.compiler.syntax.rules.FunctionToProgram;
import com.teamhandsome.compiler.syntax.rules.If;
import com.teamhandsome.compiler.syntax.rules.IfElse;
import com.teamhandsome.compiler.syntax.rules.IfStatement;
import com.teamhandsome.compiler.syntax.rules.IncrementorToModifiedAssignment;
import com.teamhandsome.compiler.syntax.rules.MathOpVariableName;
import com.teamhandsome.compiler.syntax.rules.MathOperationParam;
import com.teamhandsome.compiler.syntax.rules.MathOperationStatement;
import com.teamhandsome.compiler.syntax.rules.MathOperationToBinaryComparison;
import com.teamhandsome.compiler.syntax.rules.MathVariable;
import com.teamhandsome.compiler.syntax.rules.MethodCall;
import com.teamhandsome.compiler.syntax.rules.MethodCallToMathOperation;
import com.teamhandsome.compiler.syntax.rules.ModifiedAssignmentStatement;
import com.teamhandsome.compiler.syntax.rules.MultiDeclareVariable;
import com.teamhandsome.compiler.syntax.rules.ReturnStatement;
import com.teamhandsome.compiler.syntax.rules.StatementToBody;
import com.teamhandsome.compiler.syntax.rules.StatementToNestedStatement;
import com.teamhandsome.compiler.syntax.rules.TypeVariable;
import com.teamhandsome.compiler.syntax.rules.TypedMathOperationBodyToFunction;
import com.teamhandsome.compiler.syntax.rules.TypedVariableName;
import com.teamhandsome.compiler.syntax.rules.VariableNameStatement;
import com.teamhandsome.compiler.syntax.rules.VariableParam;
import com.teamhandsome.compiler.syntax.rules.VariableStatement;
import com.teamhandsome.compiler.syntax.rules.WhileLoopStatement;
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
				new MultiDeclareVariable(), new MathOpVariableName(), new BodyToForLoop(), new If(), new IfElse(),
				});
	}

	public SyntaxTree toTree(List<Token> tokens){
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
			node = new SyntaxNode(NodeType.MODIFIED_ASSIGNMENT, token);
        }
        else if (isComparator(token.getValue())){
			node = new SyntaxNode(NodeType.COMPARATOR, token);
        }
        else{
			node = new SyntaxNode(NodeType.SYMBOL, token);
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
	
    private SyntaxNode constructNode(NodeType type, int pieces, List<SyntaxNode> list)
    {
    	SyntaxNode retVal = new SyntaxNode(type);
        for(int i = pieces; i > 0;  i--)
        {
            retVal.addNode((list.get(list.size() - i)));
        }
        for (int i = pieces; i > 0; i--)
        {
            list.remove(list.size() - i);
        }
        return retVal;
    }
	
	private void reduce(List<SyntaxNode> nodes, SyntaxNode nextNode) {
		boolean reduce = true;
		while(reduce){
			reduce = false;
			if (nodes.size() >= 2 && nodes.get(nodes.size() - 1).getType() == NodeType.FUNCTION && nodes.get(nodes.size() - 2).getType() == NodeType.FUNCTION)
            {
                reduce = true;
                //example conversion to the line below
                //
                //TreeNode function = new TreeNode(NodeType.FUNCTION);
                //function.children.Add(nodes.get(nodes.size() - 2));
                //function.children.Add(nodes.get(nodes.size() - 1));
                //nodes.RemoveAt(nodes.size() - 2);
                //nodes.RemoveAt(nodes.size() - 1);
                //
                //nodes.add(function);

                nodes.add(constructNode(NodeType.FUNCTION, 2, nodes));
            }
            //Pr -> F
            else if (nodes.get(nodes.size() - 1).getType() == NodeType.FUNCTION && nextNode.getType() == NodeType.NULL)
            {
                reduce = true;

                ////Create new node
                //TreeNode program = new TreeNode(NodeType.PROGRAM);

                ////Add children to parent left(lowest index) to right(highest index)
                //program.children.Add(nodes.get(nodes.size() - 1));

                ////Remove children nodes from nodes
                //nodes.RemoveAt(nodes.size() - 1);

                ////add parent to nodes
                //nodes.add(program);

                nodes.add(constructNode(NodeType.PROGRAM, 1, nodes));
            }
            //F -> TMoB
            else if (nodes.size() >= 3 && nodes.get(nodes.size() - 1).getType() == NodeType.BODY && nodes.get(nodes.size() - 2).getType() == NodeType.MATH_OPERATION
                 && nodes.get(nodes.size() - 3).getType() == NodeType.TYPE)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.FUNCTION, 3, nodes));
            }
            //P -> (V)
            else if (nodes.size() >= 3 && nodes.get(nodes.size() - 1).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 1).getToken().getValue().equals(")")
                && nodes.get(nodes.size() - 2).getType() == NodeType.VARIABLE && nodes.get(nodes.size() - 3).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 3).getToken().getValue().equals("("))
            {
                reduce = true;
                nodes.add(constructNode(NodeType.PARAMETER, 3, nodes));
            }
            //P -> ()
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 1).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 1).getToken().getValue().equals(")") && nodes.get(nodes.size() - 2).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 2).getToken().getValue().equals("("))
            {
                reduce = true;
                nodes.add(constructNode(NodeType.PARAMETER, 2, nodes));
            }
            // P -> (Mo)
            else if (
                nodes.size() >= 4 && nodes.get(nodes.size() - 3).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 3).getToken().getValue().equals("(")
                && nodes.get(nodes.size() - 2).getType() == NodeType.MATH_OPERATION
                && nodes.get(nodes.size() - 1).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 1).getToken().getValue().equals(")")
                && nodes.get(nodes.size() - 4).getType() == NodeType.NAME)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.PARAMETER, 3, nodes));
            }

            //MC -> NP
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 1).getType() == NodeType.PARAMETER && nodes.get(nodes.size() - 2).getType() == NodeType.NAME)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.METHOD_CALL, 2, nodes));
            }
            //Mo -> MC
            else if (nodes.get(nodes.size() - 1).getType() == NodeType.METHOD_CALL)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.MATH_OPERATION, 1, nodes));
            }
            //Ma -> N I
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 1).getType() == NodeType.INCREMENTOR && nodes.get(nodes.size() - 2).getType() == NodeType.NAME)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.MODIFIED_ASSIGNMENT, 2, nodes));
            }
            //Bc -> Mo C Mo
            else if (nodes.size() >= 3 && nodes.get(nodes.size() - 1).getType() == NodeType.MATH_OPERATION && nodes.get(nodes.size() - 2).getType() == NodeType.COMPARATOR
              && nodes.get(nodes.size() - 3).getType() == NodeType.MATH_OPERATION)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.BINARY_COMPARATOR, 3, nodes));
            }
            //B -> {S}
            else if (nodes.size() >= 3 && nodes.get(nodes.size() - 1).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 1).getToken().getValue().equals("}")
                && nodes.get(nodes.size() - 2).getType() == NodeType.STATEMENT && nodes.get(nodes.size() - 3).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 3).getToken().getValue().equals("{"))
            {
                reduce = true;
                nodes.add(constructNode(NodeType.BODY, 3, nodes));
            }
            //S -> SS
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 1).getType() == NodeType.STATEMENT && nodes.get(nodes.size() - 2).getType() == NodeType.STATEMENT)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.STATEMENT, 2, nodes));
            }
            //S -> V;
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 1).getType() == NodeType.SEMICOLON && nodes.get(nodes.size() - 2).getType() == NodeType.VARIABLE)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.STATEMENT, 2, nodes));
            }

            //RETHINK THIS ONE
            //S -> Vn;
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 1).getType() == NodeType.SEMICOLON && nodes.get(nodes.size() - 2).getType() == NodeType.VARIABLE_NAME)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.STATEMENT, 2, nodes));
            }
            //S -> For
            else if (nodes.get(nodes.size() - 1).getType() == NodeType.FORLOOP)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.STATEMENT, 1, nodes));
            }
            //S -> return Mo;
            else if (nodes.size() >= 3 && nodes.get(nodes.size() - 1).getType() == NodeType.SEMICOLON && nodes.get(nodes.size() - 2).getType() == NodeType.MATH_OPERATION &&
              nodes.get(nodes.size() - 3).getType() == NodeType.KEYWORD && nodes.get(nodes.size() - 3).getToken().getValue().equals("return"))
            {
                reduce = true;
                nodes.add(constructNode(NodeType.STATEMENT, 3, nodes));
            }
            //S -> While
            else if (nodes.get(nodes.size() - 1).getType() == NodeType.WHILELOOP)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.STATEMENT, 1, nodes));
            }
            //S -> If
            else if (nodes.get(nodes.size() - 1).getType() == NodeType.IF && !(nextNode.getType() == NodeType.KEYWORD && nextNode.getToken().getValue().equals("else")))
            {
                reduce = true;
                nodes.add(constructNode(NodeType.STATEMENT, 1, nodes));
            }
            //S -> Mo;
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 1).getType() == NodeType.SEMICOLON && nodes.get(nodes.size() - 2).getType() == NodeType.MATH_OPERATION)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.STATEMENT, 2, nodes));
            }
            //S -> Ma;
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 1).getType() == NodeType.SEMICOLON && nodes.get(nodes.size() - 2).getType() == NodeType.MODIFIED_ASSIGNMENT)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.STATEMENT, 2, nodes));
            }
            //V -> V, N
            else if (nodes.size() >= 3 && nodes.get(nodes.size() - 3).getType() == NodeType.VARIABLE
                && nodes.get(nodes.size() - 2).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 2).getToken().getValue().equals(",")
                && nodes.get(nodes.size() - 1).getType() == NodeType.NAME)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.VARIABLE, 3, nodes));
            }
            //V -> TVn
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 1).getType() == NodeType.VARIABLE_NAME
                && nodes.get(nodes.size() - 2).getType() == NodeType.TYPE)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.VARIABLE, 2, nodes));
            }
            //V -> N E Mo
            else if (nodes.size() >= 3 && nodes.get(nodes.size() - 1).getType() == NodeType.MATH_OPERATION
               && nodes.get(nodes.size() - 2).getType() == NodeType.EQUALS && nodes.get(nodes.size() - 3).getType() == NodeType.NAME && nextNode.getType() != NodeType.OPERATOR)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.VARIABLE, 3, nodes));
            }
            //Vn->TN //check to see if its type into name but only remove name
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 1).getType() == NodeType.NAME
               && nodes.get(nodes.size() - 2).getType() == NodeType.TYPE && !(nextNode.getType() == NodeType.SYMBOL && nextNode.getToken().getValue().equals("(")))
            {
                reduce = true;
                nodes.add(constructNode(NodeType.VARIABLE_NAME, 1, nodes));
            }
            //Vn->Vn, Vn
            else if (nodes.size() >= 3 && nodes.get(nodes.size() - 1).getType() == NodeType.VARIABLE_NAME
            && nodes.get(nodes.size() - 2).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 2).getToken().getValue().equals(",")
            && nodes.get(nodes.size() - 3).getType() == NodeType.VARIABLE_NAME)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.VARIABLE_NAME, 3, nodes));
            }
            //Vn-> *T* N E Mo
            else if (nodes.size() >= 4 && nodes.get(nodes.size() - 1).getType() == NodeType.MATH_OPERATION
          && nodes.get(nodes.size() - 2).getType() == NodeType.EQUALS && nodes.get(nodes.size() - 3).getType() == NodeType.NAME
          && nodes.get(nodes.size() - 4).getType() == NodeType.TYPE)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.VARIABLE_NAME, 3, nodes));
            }
            //for -> "for" (S BCS Ma) B
            else if (nodes.size() >= 7 && nodes.get(nodes.size() - 7).getType() == NodeType.KEYWORD && nodes.get(nodes.size() - 7).getToken().getValue().equals("for")
                && nodes.get(nodes.size() - 6).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 6).getToken().getValue().equals("(")
                && nodes.get(nodes.size() - 5).getType() == NodeType.STATEMENT
                && nodes.get(nodes.size() - 4).getType() == NodeType.BINARY_COMP_STATEMENT
                && nodes.get(nodes.size() - 3).getType() == NodeType.MODIFIED_ASSIGNMENT
                && nodes.get(nodes.size() - 2).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 2).getToken().getValue().equals(")")
                && nodes.get(nodes.size() - 1).getType() == NodeType.BODY)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.FORLOOP, 7, nodes));
            }
            //if -> "if" (Bc) B
            else if (nodes.size() >= 5 && nodes.get(nodes.size() - 1).getType() == NodeType.BODY && nodes.get(nodes.size() - 2).getType() == NodeType.SYMBOL &&
            nodes.get(nodes.size() - 2).getToken().getValue().equals(")") && nodes.get(nodes.size() - 3).getType() == NodeType.BINARY_COMPARATOR &&
            nodes.get(nodes.size() - 4).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 4).getToken().getValue().equals("(") &&
            nodes.get(nodes.size() - 5).getType() == NodeType.KEYWORD && nodes.get(nodes.size() - 5).getToken().getValue().equals("if"))
            {
                reduce = true;
                nodes.add(constructNode(NodeType.IF, 5, nodes));
            }
            //S -> IF ELSE
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 1).getType() == NodeType.ELSE && nodes.get(nodes.size() - 2).getType() == NodeType.IF)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.IF, 2, nodes));
            }
            //While -> "While" (BC) B
            else if (nodes.size() >= 5 && nodes.get(nodes.size() - 1).getType() == NodeType.BODY && nodes.get(nodes.size() - 2).getType() == NodeType.SYMBOL &&
                nodes.get(nodes.size() - 2).getToken().getValue().equals(")") && nodes.get(nodes.size() - 3).getType() == NodeType.BINARY_COMPARATOR &&
                nodes.get(nodes.size() - 4).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 4).getToken().getValue().equals("(") &&
                nodes.get(nodes.size() - 5).getType() == NodeType.KEYWORD && nodes.get(nodes.size() - 5).getToken().getValue().equals("while"))
            {
                reduce = true;
                nodes.add(constructNode(NodeType.WHILELOOP, 5, nodes));
            }
            //Mo -> NP
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 1).getType() == NodeType.PARAMETER && nodes.get(nodes.size() - 2).getType() == NodeType.NAME)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.MATH_OPERATION, 2, nodes));
            }
            //Mo -> Val
            else if (nodes.get(nodes.size() - 1).getType() == NodeType.VALUE)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.MATH_OPERATION, 1, nodes));
            }
            //Mo -> (Mo)
            else if (nodes.size() >= 3 && nodes.get(nodes.size() - 1).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 1).getToken().getValue().equals(")")
                && nodes.get(nodes.size() - 2).getType() == NodeType.MATH_OPERATION && nodes.get(nodes.size() - 3).getType() == NodeType.SYMBOL
                && nodes.get(nodes.size() - 3).getToken().getValue().equals("("))
            {
                reduce = true;
                nodes.add(constructNode(NodeType.MATH_OPERATION, 3, nodes));
            }
            //Mo -> Mo Op Mo
            else if (nodes.size() >= 3 && nodes.get(nodes.size() - 1).getType() == NodeType.MATH_OPERATION &&
                nodes.get(nodes.size() - 2).getType() == NodeType.OPERATOR && nodes.get(nodes.size() - 3).getType() == NodeType.MATH_OPERATION)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.MATH_OPERATION, 3, nodes));
            }
            //Mo -> N
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 1).getType() == NodeType.NAME && !nextNode.getToken().getValue().equals("(") && !(nodes.get(nodes.size() - 2).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 2).getToken().getValue().equals(",")) && nextNode.getType() != NodeType.INCREMENTOR && nextNode.getType() != NodeType.EQUALS)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.MATH_OPERATION, 1, nodes));
            }
            //T -> T[)
            else if (nodes.size() >= 3 && nodes.get(nodes.size() - 1).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 1).getToken().getValue().equals(")") && nodes.get(nodes.size() - 2).getType() == NodeType.SYMBOL && nodes.get(nodes.size() - 2).getToken().getValue().equals("[") && nodes.get(nodes.size() - 3).getType() == NodeType.TYPE)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.TYPE, 3, nodes));
            }
            //Vn -> V = Mo
            else if (nodes.size() >= 3 && nodes.get(nodes.size() - 3).getType() == NodeType.VARIABLE
                && nodes.get(nodes.size() - 2).getType() == NodeType.EQUALS && nodes.get(nodes.size() - 1).getType() == NodeType.MATH_OPERATION)
            {
                reduce = true;
                NodeType type = NodeType.NULL;
                //Variable Name
                if (nodes.get(nodes.size() - 4).getType() == NodeType.TYPE)
                {
                    type = NodeType.VARIABLE_NAME;

                }
                else
                {
                    type = NodeType.VARIABLE;
                }

                nodes.add(constructNode(type, 3, nodes));
            }
            //BCS -> Bc;
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 2).getType() == NodeType.BINARY_COMPARATOR && nodes.get(nodes.size() - 1).getType() == NodeType.SEMICOLON)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.BINARY_COMP_STATEMENT, 2, nodes));
            }
            //Ma -> NI
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 2).getType() == NodeType.NAME && nodes.get(nodes.size() - 1).getType() == NodeType.INCREMENTOR)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.MODIFIED_ASSIGNMENT, 2, nodes));
            }
            //ELSE -> "else" B 
            else if (nodes.size() >= 2 && nodes.get(nodes.size() - 2).getType() == NodeType.KEYWORD && nodes.get(nodes.size() - 2).getToken().getValue().equals("else") && nodes.get(nodes.size() - 1).getType() == NodeType.BODY)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.ELSE, 2, nodes));
            }
            //Mo -> - Mo
            else if(nodes.size() >= 2 && nodes.get(nodes.size() - 2).getType() == NodeType.OPERATOR && nodes.get(nodes.size() - 2).getToken().getValue().equals("-")
                && nodes.get(nodes.size() - 1).getType() == NodeType.MATH_OPERATION)
            {
                reduce = true;
                nodes.add(constructNode(NodeType.MATH_OPERATION, 2, nodes));
            }
		}
	}

}
