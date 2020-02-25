package test.core;

import boomerang.BackwardQuery;
import boomerang.Query;
import boomerang.scene.Statement;
import boomerang.scene.Val;
import java.util.Optional;

public class FirstArgumentOf implements ValueOfInterestInUnit {

  private String methodNameMatcher;

  public FirstArgumentOf(String methodNameMatcher) {
    this.methodNameMatcher = methodNameMatcher;
  }

  @Override
  public Optional<? extends Query> test(Statement stmt) {
    if (!(stmt.containsInvokeExpr())) return Optional.empty();
    boomerang.scene.InvokeExpr invokeExpr = stmt.getInvokeExpr();
    if (!invokeExpr.getMethod().getName().matches(methodNameMatcher)) return Optional.empty();
    Val param = invokeExpr.getArg(0);
    if (!param.isLocal()) return Optional.empty();
    BackwardQuery newBackwardQuery = BackwardQuery.make(stmt, param);
    return Optional.<Query>of(newBackwardQuery);
  }
}
