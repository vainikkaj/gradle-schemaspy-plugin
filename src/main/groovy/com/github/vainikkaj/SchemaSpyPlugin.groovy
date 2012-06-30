package com.github.vainikkaj

import org.gradle.api.Plugin
import org.gradle.api.Project

class SchemaSpyPlugin implements Plugin<Project> {

	static final String CONFIG_NAME = 'schemaspy'
	static final String TASK_GROUP = 'schemaspy'
	static final String TASK_NAME = 'diagram'

	/*
	 * http://gradle.org/docs/current/javadoc/org/gradle/api/Project.html#task%28java.util.Map,%20java.lang.String%29
	 * http://gradle.org/docs/current/javadoc/org/gradle/api/artifacts/ConfigurationContainer.html
	 */
	void apply(Project target) {
		applyConfig target
		applyTasks target
	}

	private void applyConfig(project){
		def config = project.configurations.add(CONFIG_NAME)
		config.visible = false
		config.transitive = true
		config.description = 'Classpath for generating schemaspy diagrams'
	}

	private void applyTasks(project){
		def taskArgs = [
					type: SchemaSpyTask,
					group: TASK_GROUP,
					description: 'creates schemaspy diagram' ]
		project.task taskArgs, TASK_NAME
	}
}
