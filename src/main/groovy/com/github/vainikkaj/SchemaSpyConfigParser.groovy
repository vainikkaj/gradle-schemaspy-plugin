package com.github.vainikkaj

import net.sourceforge.schemaspy.Config

class SchemaSpyConfigParser {

	private static final String HSQLDB_MEM_REGEX = $/jdbc:hsqldb:mem:(.*)/$
	private static final String HSQLDB_FILE_REGEX = $/jdbc:hsqldb:hsql://(.*)/(.*)/$
	private static final String MYSQL_REGEX = $/jdbc:mysql://(.*)/(.*)/$
	private static final String H2_FILE_REGEX = $/jdbc:h2:(.*)/$

	Config parse(def args){
		Config c = new Config()

		def dbURL = args?.url
		switch(dbURL){
			case ~HSQLDB_MEM_REGEX:
				c.dbType = 'hsqldb-mem'
				dbURL.find(HSQLDB_MEM_REGEX){url, dbName ->
					c.db = dbName
				}
				break
			case ~HSQLDB_FILE_REGEX:
				c.dbType = 'hsqldb'
				dbURL.find(HSQLDB_FILE_REGEX){url, host, dbName ->
					c.host = host
					c.db = dbName
				}
				break
			case ~MYSQL_REGEX:
				c.dbType = 'mysql'
				dbURL.find(MYSQL_REGEX){url, host, dbName ->
					c.host = host
					c.db = dbName
				}
				break
			case ~H2_FILE_REGEX:
				c.dbType = 'h2'
				dbURL.find(H2_FILE_REGEX){url, dbName ->
					if(dbName.contains(';'))
						dbName = dbName.replaceAll(';.*','')
					if(dbName.contains('/'))
						dbName = dbName.replaceAll('.*/','')
					c.db = dbName
				}
				break
			default:
				throw new UnsupportedOperationException("'$dbURL' is not supported jdbc url")
		}

		c.user = args.user ?: 'sa'
		c.password = args.password ?: ''
		def dbDir = c.host ? "${c.dbType}-${c.host}-${c.db}" : "${c.dbType}-${c.db}"
		c.outputDir = new File("build/schemaspy/$dbDir")
		c.schema = 'PUBLIC'
		return c
	}
}
