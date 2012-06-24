package com.github.vainikkaj

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class SchemaSpyTask  extends DefaultTask {
	String greeting = 'hello from schemaspy'

	@TaskAction
	def greet() {
		println greeting
	}
}
