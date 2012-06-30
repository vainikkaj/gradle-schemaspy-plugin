package com.github.vainikkaj

import org.gradle.api.Plugin
import org.gradle.api.Project

class SchemaSpyPlugin implements Plugin<Project> {
	/*
	 * http://gradle.org/docs/current/javadoc/org/gradle/api/Project.html#task%28java.util.Map,%20java.lang.String%29
	 */
	void apply(Project target) {
		def taskArgs = [
					type: SchemaSpyTask,
					group: 'schemaspy',
					description: 'creates schemaspy diagram'
				]
		target.task(taskArgs, 'diagram')
	}
}
