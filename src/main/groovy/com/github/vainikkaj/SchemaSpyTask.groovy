package com.github.vainikkaj

import net.sourceforge.schemaspy.Config
import net.sourceforge.schemaspy.SchemaAnalyzer

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class SchemaSpyTask  extends DefaultTask {

	@TaskAction
	def createSchemaSpyDiagram() {
		def props= ['url':project.dburl]
		Config config = new SchemaSpyConfigParser().parse(props)
		new SchemaAnalyzer().analyze(config)
	}
}
