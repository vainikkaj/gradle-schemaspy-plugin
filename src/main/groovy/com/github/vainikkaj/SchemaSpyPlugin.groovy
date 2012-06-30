package com.github.vainikkaj

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.api.plugins.Convention;

/**
 * @see Project
 * @see Task
 * @see Configuration
 * @see Convention
 */
class SchemaSpyPlugin implements Plugin<Project> {

	static final String CONFIG_NAME = 'schemaspy'
	static final String TASK_GROUP = 'schemaspy'
	static final String TASK_NAME = 'diagram'

	/**
	 * @see ConfigurationContainer
	 * @see Project#task(java.util.Map, String)
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
