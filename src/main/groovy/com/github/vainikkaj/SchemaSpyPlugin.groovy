package com.github.vainikkaj

import org.gradle.api.Plugin
import org.gradle.api.Project

class SchemaSpyPlugin implements Plugin<Project> {
	void apply(Project target) {
		target.task('hello', type: SchemaSpyTask)
	}
}
