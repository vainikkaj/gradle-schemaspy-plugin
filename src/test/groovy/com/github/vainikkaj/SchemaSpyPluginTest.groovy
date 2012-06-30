package com.github.vainikkaj;

import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.*

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import org.junit.*

class SchemaSpyPluginTest {

	Project project
	Task task

	@Before
	void createGradleTestbenchProject(){
		project = ProjectBuilder.builder().build()
		project.apply plugin: 'schemaspy'
		task = project.tasks.diagram
	}

	@Test
	void 'plugin should add schemaspy task to project'() {
		assertThat task, is(SchemaSpyTask)

		assert task.name == 'diagram'
		assert task.group == 'schemaspy'
		assert task.description == 'creates schemaspy diagram'
	}
}
