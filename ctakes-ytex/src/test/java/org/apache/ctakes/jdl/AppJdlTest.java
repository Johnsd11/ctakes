/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.ctakes.jdl;

import org.apache.ctakes.jdl.data.base.JdlConnection;
import org.apache.ctakes.jdl.data.xml.jaxb.ObjectFactoryUtil;
import org.apache.ctakes.jdl.schema.xdl.JdbcType;
import org.apache.ctakes.jdl.test.PropFileMaps;
import org.apache.ctakes.jdl.test.Resources;
import org.apache.ctakes.jdl.test.SqlJdl;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assume.assumeThat;

public class AppJdlTest {
	private static JdbcType jdbc;
	private static JdlConnection jdlConnection;
	final private static String CX = Resources.CONN_X;
	final private static String D2C = Resources.DATA2C;
	final private static String D2X = Resources.DATA2X;
	final private static String L2C = Resources.LOAD2C;
	final private static String L2X = Resources.LOAD2X;

	@BeforeClass
	public static void initClass() throws JAXBException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		jdbc = ObjectFactoryUtil.getJdbcTypeBySrcXml(CX);
		jdlConnection = new JdlConnection(jdbc);
	}

	@Test
	public void execute() throws JAXBException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		assumeThat(jdbc.getDriver(), not(Resources.ENV_DRIVER));
		assumeThat(jdbc.getUrl(), not(Resources.ENV_URL));
		assumeThat(PropFileMaps.DEMO, is(true));
		demo();
	}

	public static void demo() throws JAXBException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		AppJdl appJdl;
		Connection connection = jdlConnection.getOpenConnection();
		SqlJdl.create(connection);
		// csv
		appJdl = new AppJdl(CX, D2C, L2C);
		appJdl.execute();
		SqlJdl.select(connection, true);
		SqlJdl.delete(connection);
		// xml
		appJdl = new AppJdl(CX, D2X, L2X);
		appJdl.execute();
		SqlJdl.select(connection, true);
		SqlJdl.delete(connection);
		// clear
		SqlJdl.drop(connection);
		jdlConnection.closeConnection();
	}
}
