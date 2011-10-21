package fr.opensagres.xdocreport.eclipse.demo.resume.internal.ui.editors;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.rap.singlesourcing.SingleSourcingUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import fr.opensagres.eclipse.forms.widgets.DateTimeControl;
import fr.opensagres.eclipse.forms.widgets.PhotoControl;
import fr.opensagres.xdocreport.document.images.ClassPathImageProvider;
import fr.opensagres.xdocreport.eclipse.demo.resume.domain.hr.Resume;
import fr.opensagres.xdocreport.eclipse.demo.resume.internal.ImageResources;
import fr.opensagres.xdocreport.eclipse.demo.resume.internal.Messages;
import fr.opensagres.xdocreport.eclipse.ui.FormLayoutFactory;
import fr.opensagres.xdocreport.eclipse.ui.editors.ReportingFormEditor;
import fr.opensagres.xdocreport.eclipse.ui.editors.ReportingFormPage;
import fr.opensagres.xdocreport.eclipse.utils.ByteArrayOutputStream;
import fr.opensagres.xdocreport.eclipse.utils.IOUtils;

public class OverviewPage extends ReportingFormPage<Resume> implements
		IHyperlinkListener {

	public static final String ID = "overview";
	private Text firstNameText;
	private GridData gd_lastNameText;
	private GridData gd_firstNameText;
	private Text lastNameText;
	private DateTimeControl birthDayDateTime;
	private PhotoControl photo;

	public OverviewPage(ReportingFormEditor<Resume> editor) {
		super(editor, ID, Messages.ResumeFormEditor_OverviewPage_title);
	}

	@Override
	protected Image getFormTitleImage() {
		return ImageResources.getImage(ImageResources.IMG_OVERVIEW);
	}

	@Override
	protected void fillBody(IManagedForm managedForm, FormToolkit toolkit) {
		Composite body = managedForm.getForm().getBody();
		body.setLayout(FormLayoutFactory.createFormTableWrapLayout(true, 2));

		Composite left = toolkit.createComposite(body);
		left.setLayout(FormLayoutFactory
				.createFormPaneTableWrapLayout(false, 1));
		left.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		// General info section
		createGeneralInfoSection(toolkit, left);

		Composite right = toolkit.createComposite(body);
		right.setLayout(FormLayoutFactory.createFormPaneTableWrapLayout(false,
				1));
		right.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		// Content section
		createContentSection(toolkit, right);

	}

	private void createGeneralInfoSection(FormToolkit toolkit, Composite left) {
		Section section = toolkit.createSection(left, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setDescription(Messages.ResumeFormEditor_OverviewPage_GeneralInfo_desc);
		section.setText(Messages.ResumeFormEditor_OverviewPage_GeneralInfo_title);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(data);

		Composite sbody = toolkit.createComposite(section);
		section.setClient(sbody);

		GridLayout glayout = new GridLayout();
		// glayout.horizontalSpacing = 10;
		glayout.numColumns = 2;
		sbody.setLayout(glayout);

		// First name
		toolkit.createLabel(sbody, Messages.ResumeFormEditor_FirstName_label);
		firstNameText = toolkit.createText(sbody, "", SWT.SINGLE);
		gd_firstNameText = new GridData(GridData.FILL_HORIZONTAL);
		gd_firstNameText.widthHint = 150;
		firstNameText.setLayoutData(gd_firstNameText);

		// Last name
		toolkit.createLabel(sbody, Messages.ResumeFormEditor_LastName_label);
		lastNameText = toolkit.createText(sbody, "", SWT.SINGLE);
		gd_lastNameText = new GridData(GridData.FILL_HORIZONTAL);
		gd_lastNameText.widthHint = 150;
		lastNameText.setLayoutData(gd_lastNameText);

		// Birthday
		toolkit.createLabel(sbody, Messages.ResumeFormEditor_Birthday_label);
		birthDayDateTime = new DateTimeControl(sbody, SWT.NONE, SWT.SINGLE,
				SWT.FLAT, toolkit);
		GridData gd_birthDayDateTimet = new GridData(GridData.FILL_HORIZONTAL);
		gd_birthDayDateTimet.widthHint = 150;
		birthDayDateTime.setLayoutData(gd_birthDayDateTimet);
		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit,
				birthDayDateTime);

		// Photo
		Label photoLabel = toolkit.createLabel(sbody,
				Messages.ResumeFormEditor_Photo_label);
		GridData gd_photoLabel = new GridData();
		gd_photoLabel.verticalAlignment = SWT.TOP;
		photoLabel.setLayoutData(gd_photoLabel);
		photo = new PhotoControl(sbody, SWT.NONE, SWT.BORDER, toolkit);
		GridData gd_photo = new GridData(GridData.FILL_HORIZONTAL);
		gd_photo.widthHint = 150;
		photo.setLayoutData(gd_photo);
		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, photo);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, sbody);
	}

	private void createContentSection(FormToolkit toolkit, Composite parent) {
		Section section = toolkit.createSection(parent, Section.TITLE_BAR);
		section.setText(Messages.ResumeFormEditor_OverviewPage_ResumeContent_title);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(data);

		Composite sbody = toolkit.createComposite(section);
		section.setClient(sbody);

		Composite container = createStaticSectionClient(toolkit, section);

		FormText text = createClient(container,
				Messages.ResumeFormEditor_OverviewPage_ResumeContent_content,
				toolkit);
		text.setImage("experiences_page",
				ImageResources.getImage(ImageResources.IMG_EXPERIENCES));
		text.setImage("skills_page",
				ImageResources.getImage(ImageResources.IMG_SKILLS));
		section.setClient(container);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, sbody);
	}

	protected final FormText createClient(Composite section, String content,
			FormToolkit toolkit) {
		FormText text = toolkit.createFormText(section, true);
		try {
			text.setText(content, true, false);
		} catch (SWTException e) {
			text.setText(e.getMessage(), false, false);
		}
		text.addHyperlinkListener(this);
		return text;
	}

	protected Composite createStaticSectionClient(FormToolkit toolkit,
			Composite parent) {
		Composite container = toolkit.createComposite(parent, SWT.NONE);
		container.setLayout(FormLayoutFactory
				.createSectionClientTableWrapLayout(false, 1));
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		container.setLayoutData(data);
		return container;
	}

	@Override
	protected void onBind(DataBindingContext bindingContext) {
		onBindGeneralInfo(bindingContext);

	}

	private void onBindGeneralInfo(DataBindingContext bindingContext) {
		IObservableValue firstNameTextObserveTextObserveWidget = SWTObservables
				.observeText(firstNameText, SWT.Modify);
		IObservableValue getModel1FirstNameObserveValue = PojoObservables
				.observeValue(getModelObject().getOwner(), "firstName");
		bindingContext.bindValue(firstNameTextObserveTextObserveWidget,
				getModel1FirstNameObserveValue, null, null);
		//
		IObservableValue lastNameTextObserveTextObserveWidget = SWTObservables
				.observeText(lastNameText, SWT.Modify);
		IObservableValue getModel1LastNameObserveValue = PojoObservables
				.observeValue(getModelObject().getOwner(), "lastName");
		bindingContext.bindValue(lastNameTextObserveTextObserveWidget,
				getModel1LastNameObserveValue, null, null);

		// TODO : bind image photo with IImageProvider of the model.
		// for the moment, just load the image from the model		
		try {
			photo.setImageStream(getModelObject().getPhotoAsStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		IObservableValue photoObserveImageObserveWidget = SWTObservables
//				.observeImage(photo.getPhotoLabel());
//		IObservableValue getModelPhotoObserveValue = PojoObservables
//				.observeValue(getModelObject().getOwner(), "lastName");
//		bindingContext.bindValue(lastNameTextObserveTextObserveWidget,
//				getModel1LastNameObserveValue, null, null);

	}

	public void linkActivated(HyperlinkEvent e) {
		String href = (String) e.getHref();
		getEditor().setActivePage(href);
	}

	public void linkEntered(HyperlinkEvent e) {
		// Do nothing
	}

	public void linkExited(HyperlinkEvent e) {
		// Do nothing
	}

}
