package com.github.vainikkaj.schemaspy;

import static org.junit.Assert.*;

import net.sourceforge.schemaspy.Config
import net.sourceforge.schemaspy.SchemaAnalyzer

import org.junit.After
import org.junit.Before
import org.junit.Rule;
import org.junit.Test
import org.junit.rules.TemporaryFolder;

import groovy.sql.Sql

class HsqldbMemorySmokeTests {

	@Rule
	public TemporaryFolder tempDir = new TemporaryFolder()

	static String DB = 'myunittests'
	static String URL = 'jdbc:hsqldb:mem:'+DB
	static String USER = 'sa'
	static String PASSWORD = ''
	static String DRIVER = 'org.hsqldb.jdbcDriver'

	static String OUTPUT = 'build/tst'

	def sql

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
	void 'junit test can connect to in-memory db'(){
		assertDatabaseState()
		sql.eachRow("select * from LIDETAIL") {println "Gromit likes ${it.comment}" }
	}

	@Test
	void 'schemaspy can access in-memory db'(){
		assertDatabaseState()

		def dir = tempDir.newFolder()
		def conf = new Config()

		conf.dbType = "hsqldb-mem"
		conf.db = DB
		conf.outputDir = dir
		conf.user = 'sa'
		conf.password = ''
		conf.schema = 'PUBLIC'

		/*
		 conf.setHost 'localhost'
		 conf.setHighQuality false
		 conf.setNumRowsEnabled false
		 conf.setAdsEnabled false		
		 */
		def sa = new SchemaAnalyzer()
		sa.analyze(conf)
	}
}
