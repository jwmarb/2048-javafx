package org.csc335.model_tests;

import org.csc335.models.DialogModel;
import org.junit.Test;

public class DialogModelTest {

    @Test
    public void testTitle() {
        DialogModel dialog = new DialogModel();

        assert (dialog.getTitle() == null);

        dialog.setTitle("Testing");

        assert (dialog.getTitle().equals("Testing"));
        assert (dialog.titleProperty() != null);
        assert (dialog.titleProperty().getValue().equals("Testing"));
    }

    @Test
    public void testDescription() {
        DialogModel dialog = new DialogModel();

        assert (dialog.getDescription() == null);

        dialog.setDescription("This is a test");

        assert (dialog.getDescription().equals("This is a test"));
        assert (dialog.descriptionProperty() != null);
        assert (dialog.descriptionProperty().getValue().equals("This is a test"));
    }
}
