package rodrigo.study.hibernate.generatesql;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.internal.Formatter;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.hibernate.tool.hbm2ddl.SchemaUpdateScript;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class will create an hibernate {@link Configuration} with the given dialect and will scan provided
 * package for {@link MappedSuperclass} and {@link Entity}.
 * You can then use the export methods to generate your schema DDL. 
 * 
 * @author Geoffroy Warin (https://github.com/geowarin)
 *
 */
public class HibernateExporter {

	private static Logger log = LoggerFactory.getLogger(HibernateExporter.class);
	
	private String dialect;
	private String entityPackage;
	
	private boolean generateCreateQueries = false;
	private boolean generateDropQueries = false;
	private boolean generateSchemaUpdateScript = false;

	private Configuration hibernateConfiguration;
	
	public HibernateExporter(String dialect, String entityPackage) {
		this.dialect = dialect;
		this.entityPackage = entityPackage;
		
		hibernateConfiguration = createHibernateConfig();
	}
	
	public void export(OutputStream out, boolean generateCreateQueries, boolean generateDropQueries) {
		
		Dialect hibDialect = Dialect.getDialect(hibernateConfiguration.getProperties());
		try (PrintWriter writer = new PrintWriter(out)) {
			
			if (generateCreateQueries) {
				String[] createSQL = hibernateConfiguration.generateSchemaCreationScript(hibDialect);
				write(writer, createSQL, FormatStyle.DDL.getFormatter());
			}
			if (generateDropQueries) {
				String[] dropSQL = hibernateConfiguration.generateDropSchemaScript(hibDialect);
				write(writer, dropSQL, FormatStyle.DDL.getFormatter());
			}
			if (generateSchemaUpdateScript) {
				Connection connection = null;
				DatabaseMetadata databaseMetadata = null;
				try {
					connection = DriverManager.getConnection("jdbc:mysql://localhost/hibernateStudy", "root", "root");
					databaseMetadata = new DatabaseMetadata(connection , hibDialect, hibernateConfiguration);
					
					//String[] updateSQL = hibernateConfiguration.generateSchemaUpdateScript(hibDialect, databaseMetadata);
					List<SchemaUpdateScript> updateScriptList = hibernateConfiguration.generateSchemaUpdateScriptList(hibDialect, databaseMetadata);
					write(writer, updateScriptList, FormatStyle.DDL.getFormatter());
					//write(writer, updateSQL, FormatStyle.DDL.getFormatter());
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	public void export(File exportFile) throws FileNotFoundException {
		
		export(new FileOutputStream(exportFile), generateCreateQueries, generateDropQueries);
	}
	
	public void exportToConsole() {
		
		export(System.out, generateCreateQueries, generateDropQueries);
	}
	
	private void write(PrintWriter writer, String[] lines, Formatter formatter) {
		
		for (String string : lines)
			writer.println(formatter.format(string) + ";");
	}
	
	private void write(PrintWriter writer, List<SchemaUpdateScript> updateScriptList, Formatter formatter) {
		
		for (SchemaUpdateScript schemaUpdateScript : updateScriptList) {
			writer.println(formatter.format(schemaUpdateScript.getScript()) + ";");
		}
	}

	private Configuration createHibernateConfig() {
		
		hibernateConfiguration = new Configuration();

		final Reflections reflections = new Reflections(entityPackage);
		for (Class<?> cl : reflections.getTypesAnnotatedWith(MappedSuperclass.class)) {
			hibernateConfiguration.addAnnotatedClass(cl);
			log.info("Mapped = " + cl.getName());
		}
		for (Class<?> cl : reflections.getTypesAnnotatedWith(Entity.class)) {
			hibernateConfiguration.addAnnotatedClass(cl);
			log.info("Mapped = " + cl.getName());
		}
		hibernateConfiguration.setProperty(AvailableSettings.DIALECT, dialect);
		return hibernateConfiguration;
	}

	public boolean isGenerateDropQueries() {
		return generateDropQueries;
	}

	public void setGenerateDropQueries(boolean generateDropQueries) {
		this.generateDropQueries = generateDropQueries;
	}

	public Configuration getHibernateConfiguration() {
		return hibernateConfiguration;
	}

	public void setHibernateConfiguration(Configuration hibernateConfiguration) {
		this.hibernateConfiguration = hibernateConfiguration;
	}

	public boolean isGenerateSchemaUpdateScript() {
		return generateSchemaUpdateScript;
	}

	public void setGenerateSchemaUpdateScript(boolean generateSchemaUpdateScript) {
		this.generateSchemaUpdateScript = generateSchemaUpdateScript;
	}

	public boolean isGenerateCreateQueries() {
		return generateCreateQueries;
	}

	public void setGenerateCreateQueries(boolean generateCreateQueries) {
		this.generateCreateQueries = generateCreateQueries;
	}
	
	
	
	
}
