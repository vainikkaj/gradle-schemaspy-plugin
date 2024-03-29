package com.github.vainikkaj.schemaspy;

import static org.junit.Assert.*;

import net.sourceforge.schemaspy.Config
import net.sourceforge.schemaspy.SchemaAnalyzer

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.After
import org.junit.Before
import org.junit.Rule;
import org.junit.Test
import org.junit.rules.TemporaryFolder;

import com.github.vainikkaj.SchemaSpyTask

import groovy.sql.Sql

class HsqldbMemorySmokeTests {

	@Rule
	public TemporaryFolder tempDir = new TemporaryFolder()

	static String DB = 'myunittests'
	static String URL = "jdbc:hsqldb:mem:$DB"
	static String USER = 'sa'
	static String PASSWORD = ''
	static String DRIVER = 'org.hsqldb.jdbcDriver'

	def sql
	def task

	@Before
	void createTask(){
		Project project = ProjectBuilder.builder().build()
		project.ext.dburl = URL
		task = project.task('mytaskname', type: SchemaSpyTask)
	}

	@Before
	void 'setup database'(){
		sql =  Sql.newInstance(url: URL, user: USER, password: PASSWORD, driver:DRIVER);

		sql.execute '''
		DROP TABLE IF EXISTS LIDETAIL;
		CREATE MEMORY TABLE LIDETAIL (
		  invoice_id BIGINT NOT NULL,
		  row_number BIGINT NOT NULL,
		  comment VARCHAR(255)
		  );
		'''

		sql.execute """
		INSERT INTO PUBLIC.LIDETAIL (invoice_id, row_number, comment) values (2, 1, 'Gift wrapping requested');
		INSERT INTO LIDETAIL (invoice_id, row_number, comment) values (3, 2, 'Gold trim');
		"""
	}

	@After
	void 'teardown database'(){
		sql.execute "SHUTDOWN"
		sql.close();
	}

	private void assertDatabaseState(){
		sql.eachRow("select count(*) as size from LIDETAIL") {assert it.size == 2 }
	}

	@Test
	void 'junit can access in-memory db'(){
		assertDatabaseState()
		sql.eachRow("select * from LIDETAIL") {println "Gromit likes ${it.comment}" }
	}

	@Test
	void 'schemaspy api can access in-memory db'(){
		assertDatabaseState()

		def outputDir = tempDir.newFolder()
		def conf = new Config()

		conf.dbType = "hsqldb-mem"
		conf.db = DB
		conf.outputDir = outputDir
		conf.user = 'sa'
		conf.password = ''
		conf.schema = 'PUBLIC'

		/*
		 conf.setHost 'localhost'
		 conf.setHighQuality false
		 conf.setNumRowsEnabled false
		 conf.setAdsEnabled false		
		 */

		new SchemaAnalyzer().analyze(conf)
		def tmpReport = new File(outputDir, 'index.html')
		assert tmpReport.exists()
	}

	@Test
	void 'schemaspy task can access in-memory db'(){
		assertDatabaseState()
		task.createSchemaSpyDiagram()
	}
}
