package jacoco.aggregator.plugin.bug.replicator;

import org.gradle.api.Project;
import org.gradle.api.Plugin;
import org.gradle.api.attributes.TestSuiteType;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JvmTestSuitePlugin;
import org.gradle.api.reporting.ReportingExtension;
import org.gradle.testing.jacoco.plugins.JacocoCoverageReport;
import org.gradle.testing.jacoco.plugins.JacocoReportAggregationPlugin;

public class JacocoAggregatorPluginBugReplicatorPlugin implements Plugin<Project> {
    public void apply(Project project) {
        String option = project.getExtensions().getExtraProperties().get("option").toString();
        switch (option) {
            case "apply_java_plugin":
                project.getPluginManager().apply(JavaPlugin.class);
                project.getPluginManager().apply(JacocoReportAggregationPlugin.class);
                break;
            case "apply_jvm_test_suites":
                project.getPluginManager().apply(JvmTestSuitePlugin.class);
                project.getPluginManager().apply(JacocoReportAggregationPlugin.class);
                break;
            case "register_report":
                project.getPluginManager().apply(JacocoReportAggregationPlugin.class);
                registerReport(project);
                break;
            case "apply_jvm_test_suites_and_register_report":
                project.getPluginManager().apply(JvmTestSuitePlugin.class);
                project.getPluginManager().apply(JacocoReportAggregationPlugin.class);
                registerReport(project);
                break;
        }
    }

    private void registerReport(Project project) {
        ReportingExtension reportingExtension =
                project.getExtensions().getByType(ReportingExtension.class);
        reportingExtension
                .getReports()
                .register("testCodeCoverageReport", JacocoCoverageReport.class, jacocoCoverageReport -> {
                    jacocoCoverageReport.getTestType().set(TestSuiteType.UNIT_TEST);
                });
    }
}
