package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;
import com.teamhandsome.interfaces.IRule;

public abstract class SyntaxRule implements IRule{
	
    protected SyntaxNode constructNode(NodeType type, int pieces, List<SyntaxNode> list)
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
	
}
