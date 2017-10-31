
import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.core.dom.ast.cpp.*;

/**
 * 邱景 创建于 2017/1/24.
 * 用途：CPP语法单元遍历
 */
public class CppASTVisitor extends ASTVisitor {

    public CppASTVisitor() {

        shouldVisitAmbiguousNodes = true;
        shouldVisitArrayModifiers = true;
        shouldVisitBaseSpecifiers = false;
        shouldVisitDeclarations = true;
        shouldVisitDeclarators = true;
        shouldVisitDeclSpecifiers = false;
        shouldVisitDesignators = true;
        shouldVisitEnumerators = true;
        shouldVisitExpressions = true;
        shouldVisitImplicitNameAlternates = true;
        shouldVisitImplicitNames = true;
        shouldVisitInitializers = true;
        shouldVisitNames = true;
        shouldVisitNamespaces = true;
        shouldVisitParameterDeclarations = true;
        shouldVisitPointerOperators = true;
        shouldVisitProblems = false;
        shouldVisitStatements = true;
        shouldVisitTemplateParameters = true;
        shouldVisitTranslationUnit = true;
        shouldVisitTypeIds = true;
    }


    /**
     * 返回是否需要对指定语法单元进行索引
     *
     * @param id 语法单元ID
     * @return true 索引 false 不索引
     */
    public Boolean need_index(int id) {
        return true;//syntax_units.charAt(id) == '1';
    }

    /**
     * 索引
     *
     * @param node  结点
     * @param field 域
     */
    public void index(IASTNode node, int field) {
        //document.add(new Field(Value.CPP_FIELDS[field], node.getRawSignature(), TextField.TYPE_NOT_STORED));
    }

    // 索引类名和函数名
    @Override
    public int visit(IASTName name) {
        IASTNode parent = name.getParent();
        if (parent instanceof ICPPASTCompositeTypeSpecifier) { // 类
//            if (need_index(Value.CPP_CLASS_ID)) {
//                index(name, Value.CPP_CLASS_ID);
//            }

        } else if (parent instanceof ICPPASTFunctionDeclarator) {// 函数
//            if (need_index(Value.CPP_FUNCTION_ID)) {
//                index(name, Value.CPP_FUNCTION_ID);
//            }
        } else if (parent instanceof ICPPASTArrayDeclarator) {// 数组, 索引完整的声明
//            if (need_index(Value.CPP_ARRAY_ID)) {
//                index(parent.getParent(), Value.CPP_ARRAY_ID);
//            }
        } else if (parent instanceof ICPPASTUsingDirective) {// 命名空间
//            if (need_index(Value.CPP_NAMESPACE_ID)) {
//                index(name, Value.CPP_NAMESPACE_ID);
//            }
        }

        return ASTVisitor.PROCESS_CONTINUE;
    }

    // typedef 声明
    public int visit(IASTDeclaration declaration) {
        if (declaration instanceof IASTSimpleDeclaration) {
            String decl = declaration.getRawSignature();
            if (decl.startsWith("typedef")) {
                // typedef 声明
//                index(declaration, Value.CPP_TYPEDEF_ID);
            } else if (decl.startsWith("enum")) {
//                index(declaration, Value.CPP_ENUM_ID);
            }
        }
        return ASTVisitor.PROCESS_CONTINUE;
    }

    // 常量
    public int visit(IASTExpression expression) {
//        if (need_index(Value.CPP_CONSTANT_ID)) {
//            if (expression instanceof ICPPASTLiteralExpression) {
//                index(expression, Value.CPP_CONSTANT_ID);
//            }
//        }
        return ASTVisitor.PROCESS_CONTINUE;
    }

    // 指针
    public int visit(IASTDeclarator declarator) {
//        if (need_index(Value.CPP_POINTER_ID)) {
//            if (declarator instanceof CPPASTDeclarator) {
//                IASTNode[] children = declarator.getChildren();
//                if (children != null && children.length > 0 && children[0] instanceof CPPASTPointer) {
//                    index(declarator.getParent(), Value.CPP_POINTER_ID);
//                }
//            }
//        }
        return ASTVisitor.PROCESS_CONTINUE;
    }

    // 异常, 循环, 返回
    public int visit(IASTStatement statement) {
        // 异常
//        if (need_index(Value.CPP_EXCEPTION_ID)) {
//            if (statement instanceof ICPPASTTryBlockStatement) {
//                index(statement, Value.CPP_EXCEPTION_ID);
//            }
//        }

        // 循环
//        if (need_index(Value.CPP_LOOP_ID)) {
//            if (statement instanceof CPPASTWhileStatement || statement instanceof CPPASTForStatement || statement instanceof CPPASTDoStatement) {
//                index(statement, Value.CPP_LOOP_ID);
//            }
//        }

        // 返回语句
//        if (need_index(Value.CPP_RETURN_ID)) {
//            if (statement instanceof CPPASTReturnStatement) {
//                index(statement, Value.CPP_RETURN_ID);
//            }
//        }
        return ASTVisitor.PROCESS_CONTINUE;
    }

    // 模板
    public int visit(ICPPASTTemplateParameter templateParameter) {
//        if (need_index(Value.CPP_TEMPLATE_ID)) {
//            index(templateParameter.getParent(), Value.CPP_TEMPLATE_ID);
//        }
        return ASTVisitor.PROCESS_CONTINUE;
    }
}
