package com.github.vainikkaj

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

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
		project.configurations.add(CONFIG_NAME){
			visible = false
			transitive = true
			description = 'Classpath for generating schemaspy diagrams'
		}
	}

	private void applyTasks(project){
		project.task(TASK_NAME, type: SchemaSpyTask){
			group = TASK_GROUP
			description = 'creates schemaspy diagram'
		}
	}
}
