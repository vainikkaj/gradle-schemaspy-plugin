package com.github.vainikkaj.schemaspy

import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.*


import net.sourceforge.schemaspy.Config
import net.sourceforge.schemaspy.util.ConnectionURLBuilder;

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class HsqldbMemoryConfigTests {

	@Rule
	public TemporaryFolder tempDir = new TemporaryFolder()

	Config config
	Properties dbProperties


	static final String CUSTOM_IN_MEMORY_PROPERTIES_FILE = 'hsqldb-mem'

	@Before
	void 'init collaborators'(){
		config = new Config()
		assert config.dbPropertiesLoadedFrom.endsWith('ora.properties')
		dbProperties = null
	}

	@Test
	void 'required connectionURL should overrideable'(){
		assert createConnectionURL('abc') == 'jdbc:hsqldb:mem:abc'
		assert createConnectionURL('xyz') == 'jdbc:hsqldb:mem:xyz'
	}

	@Test
	void 'default dbProperties should be loaded from file'(){
		createConnectionURL('whatever')

		assert dbProperties.size() == 5
		assert dbProperties.connectionSpec == 'jdbc:hsqldb:mem:<db>'
		assert dbProperties.description ==  'HSQLDB in-memory'
		assert dbProperties.host == 'hostname[:port] (possibly just localhost)'
		assert dbProperties.driver == 'org.hsqldb.jdbcDriver'
		assert dbProperties.driverPath == ''
	}

	private String createConnectionURL(String fakeDb){
		config.dbType = CUSTOM_IN_MEMORY_PROPERTIES_FILE
		dbProperties = config.getDbProperties(config.dbType)
		assert config.dbPropertiesLoadedFrom.endsWith('hsqldb-mem.properties')

		config.db = fakeDb
		config.outputDir = tempDir.newFolder()
		config.user = 'sa'
		config.password = ''

		ConnectionURLBuilder urlBuilder = new ConnectionURLBuilder(config, dbProperties)
		urlBuilder.connectionURL
	}
}
