package jlox;

public class Interpreter implements Expr.Visitor<Object> {
    
    
    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
      return expr.value;
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
      return evaluate(expr.expression);
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        Object right = evaluate(expr.right);

        switch (expr.operator.type) {
            case BANG:
                return !isTruthy(right);
            case MINUS:
                return -(double)right;
        }

        // Unreachable.
        return null;
    }

    private boolean isTruthy(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) return (boolean)object;
        return true;
      }

      @Override
      public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right); 
    
        switch (expr.operator.type) {
          case MINUS:
            return (double)left - (double)right;
          case SLASH:
            return (double)left / (double)right;
          case STAR:
            return (double)left * (double)right;
        }
    
        // Unreachable.
        return null;
      }
}
