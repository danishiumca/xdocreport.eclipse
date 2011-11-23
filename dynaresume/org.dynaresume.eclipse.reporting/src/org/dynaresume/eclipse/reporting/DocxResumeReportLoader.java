package org.dynaresume.eclipse.reporting;

import java.io.IOException;
import java.io.InputStream;

import fr.opensagres.xdocreport.eclipse.reporting.core.ReportException;
import fr.opensagres.xdocreport.eclipse.reporting.xdocreport.core.XDocReportLoader;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import fr.opensagres.xdocreport.template.textstyling.SyntaxKind;

public class DocxResumeReportLoader extends XDocReportLoader {

	public InputStream doGetSourceStream() throws IOException, ReportException {
		return ResumeReportProcessor.class.getResourceAsStream("Resume.docx");
	}

	@Override
	public String getTemplateEngineKind() {
		return TemplateEngineKind.Velocity.name();
	}

	@Override
	public FieldsMetadata getFieldsMetadata() {
		FieldsMetadata metadata = new FieldsMetadata();
		metadata.addFieldAsImage("photo");
		metadata.addFieldAsList("experiences.Title");
		metadata.addFieldAsList("experiences.Detail");
		metadata.addFieldAsTextStyling("experiences.Detail", SyntaxKind.Html);
		metadata.addFieldAsList("educations.Label");
		metadata.addFieldAsList("educations.DateYear");
		metadata.addFieldAsList("hobbies.Label");
		return metadata;
	}

}