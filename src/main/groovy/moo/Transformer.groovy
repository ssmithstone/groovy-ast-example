package moo

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassHelper
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
    private static final ClassNode TRANSFORM_ANNOTATION_NODE = ClassHelper.make(TransformerAnn)
    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

        def methodNode = astNodes[1]
        def methods = methodNode.declaringClass.methods

        methods.findAll {MethodNode node ->
            node.getAnnotations(TRANSFORM_ANNOTATION_NODE)
        }.each {  MethodNode node ->
            def startMessage = createPrintlnAst("Starting $node.name\n")
            def existingStatements = node.code.statements
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
