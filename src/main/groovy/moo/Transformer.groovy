package moo

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class Transformer implements ASTTransformation {

    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

        def methods = sourceUnit.getAST()?.getMethods();

        methods.findAll {MethodNode node ->
            node.getAnnotations(new ClassNode(TransformerAnn))
        }.each {  MethodNode node ->
            Statement startMessage = createPrintlnAst("Starting $node.name")
            List existingStatements = method.getCode().getStatements()
            existingStatements.add(0, startMessage)
        }


    }

    private Statement createPrintlnAst(String message) {
        return new ExpressionStatement(
                new MethodCallExpression(
                        new VariableExpression("this"),
                        new ConstantExpression("println"),
                        new ArgumentListExpression(
                                new ConstantExpression(message)
                        )
                )
        )
    }
}
