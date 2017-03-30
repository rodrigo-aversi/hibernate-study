package rodrigo.study.hibernate.generatesql;

import java.io.File;
import java.io.IOException;

public class GenerateDdlApp {
	
	public static void main(String[] args) throws IOException {

		HibernateExporter exporter = new HibernateExporter("org.hibernate.dialect.MySQL5Dialect", "rodrigo.study.hibernate.model");
		exporter.setGenerateSchemaUpdateScript(true);
		exporter.exportToConsole();
		exporter.export(new File("schema.sql"));
	}
}
