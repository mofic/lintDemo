package com.mofic.lintlib;

import com.android.tools.lint.detector.api.*;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UCallExpression;

import java.util.Arrays;
import java.util.List;

/**
 * @author lanweining
 * @Date 2019/2/3 3:38 PM
 */
public class LogDetector extends Detector implements Detector.UastScanner {

    public static final Issue ISSUE = Issue.create(
            "LogUsage",
            "请勿直接使用android.util.Log",
            "请勿直接使用android.util.Log，请使用封装的log工具类",
            Category.CORRECTNESS,
            6,
            Severity.WARNING,
            new Implementation(LogDetector.class, Scope.JAVA_FILE_SCOPE)
    );

    @Nullable
    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("v", "d", "i", "w", "e", "wtf");
    }

    @Override
    public void visitMethodCall(@NotNull JavaContext context, @NotNull UCallExpression node, @NotNull PsiMethod method) {
        super.visitMethodCall(context, node, method);
        if (context.getEvaluator().isMemberInClass(method, "android.util.Log")) {
            context.report(ISSUE, node, context.getCallLocation(node, true, false), "最好不要直接使用android.util.Log");
        }
    }
}
