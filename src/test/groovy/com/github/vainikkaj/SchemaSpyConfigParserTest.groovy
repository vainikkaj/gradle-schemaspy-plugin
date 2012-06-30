package com.github.vainikkaj;

import static org.junit.Assert.*;

import net.sourceforge.schemaspy.Config

import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class SchemaSpyConfigParserTest {

	private static final String FAKE_DB = 'mydb123'
	private static final String FAKE_USER = 'abc123'
	private static final String FAKE_PASSWORD = 'def123'

	def final fakeCreds = ['user':FAKE_USER, 'password':FAKE_PASSWORD]
	def final hsqldbMem = ['url':"jdbc:hsqldb:mem:$FAKE_DB"]
	def final hsqldbFile = ['url':"jdbc:hsqldb:hsql://localhost/$FAKE_DB"]

	def final mysql = ['url':"jdbc:mysql://localhost/$FAKE_DB"]
	def final h2absolutePath = ['url':"jdbc:h2:/somepath/$FAKE_DB;FILE_LOCK=NO"]
	def final h2relativePath = ['url':"jdbc:h2:$FAKE_DB"]

	SchemaSpyConfigParser parser
	Config config

	@Before
	void 'init parser'(){
		parser = new SchemaSpyConfigParser()
		config = null
	}

	private void parseConfig(args){
		config = parser.parse(args)
	}

	@Test(expected=UnsupportedOperationException.class)
	void 'empty args shoud throw exception'(){
		parseConfig()
	}
	
	@Test
	void 'error messege should have descriptive text'(){
		try {
			parseConfig()
		} catch (Exception e) {
			assert e.message == /'null' is not supported jdbc url/
		}
	}

	@Test
	void 'db specific config should be determined from jdbc url'(){
		parseConfig hsqldbMem
		assert config.dbType == 'hsqldb-mem'
		assert config.db == FAKE_DB

		parseConfig hsqldbFile
		assert config.dbType == 'hsqldb'
		assert config.db == FAKE_DB

		parseConfig mysql
		assert config.dbType == 'mysql'
		assert config.db == FAKE_DB
		
		parseConfig h2absolutePath
		assert config.dbType == 'h2'
		assert config.db == FAKE_DB
		
		parseConfig h2relativePath
		assert config.dbType == 'h2'
		assert config.db == FAKE_DB
	}

	@Test
	void 'default credentials should be used as fallback'(){
		parseConfig hsqldbMem + fakeCreds
		assert config.user == FAKE_USER
		assert config.password == FAKE_PASSWORD

		parseConfig hsqldbMem
		assert config.user == 'sa'
		assert config.password == ''
	}

	@Test
	void 'default output dir should be used as fallback'(){
		parseConfig hsqldbMem
		assert config.outputDir == new File("build/schemaspy/hsqldb-mem-$FAKE_DB")

		parseConfig hsqldbFile
		assert config.outputDir == new File("build/schemaspy/hsqldb-localhost-$FAKE_DB")

		parseConfig mysql
		assert config.outputDir == new File("build/schemaspy/mysql-localhost-$FAKE_DB")
	}

	@Ignore("examine best way to test system properties with groovy")
	@Test
	void 'parser should try to read db values from existing system properties'(){
		fail
	}
}
