package jacoco.aggregator.plugin.bug.replicator;

import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.api.Project;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class JacocoAggregatorPluginBugReplicatorPluginTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "apply_java_plugin",
            "apply_jvm_test_suites",
            "register_report",
            "apply_jvm_test_suites_and_register_report",
    })
    void pluginAddsTestCodeCoverageReport(String option) {
        Project project = ProjectBuilder.builder().build();
        project.getExtensions().getExtraProperties().set("option", option);
        project.getPlugins().apply("jacoco.aggregator.plugin.bug.replicator");
        assertNotNull(project.getTasks().findByName("testCodeCoverageReport"));
    }
}
