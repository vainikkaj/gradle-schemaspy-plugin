package com.github.vainikkaj;

import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.*

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.*

class SchemaSpyPluginTest {

	Project project

	@Before
	void createGradleTestbenchProject(){
		project = ProjectBuilder.builder().build()
		project.apply plugin: 'schemaspy'
	}

	@Test
	void 'plugin should add schemaspy task to project'() {
		assertThat project.tasks.diagram, is(SchemaSpyTask)
	}
}
