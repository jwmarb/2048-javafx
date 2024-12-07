package org.csc335.model_tests;

import org.csc335.interfaces.DialogActionListener;
import org.csc335.models.DialogModel;
import org.junit.jupiter.api.Test;

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


    private boolean dialogVisibleFlag = false;
    @Test
    public void testListeningHidden() {
        DialogModel dialog = new DialogModel();

        dialog.addListener(new DialogActionListener() {
            @Override
            public void dialogShown() {
              dialogVisibleFlag = true;
            }
      
            @Override
            public void dialogHidden() {
              dialogVisibleFlag = false;
            }
      
            @Override
            public void dialogAction(int childIdx) {}
        });
        assert (!dialogVisibleFlag);

        dialog.setHidden(false);
        assert (dialogVisibleFlag);

        dialog.setHidden(true);
        assert (!dialogVisibleFlag);
    }


    private int dialogActionParam = 0;
    @Test
    public void testInvokeActionListening() {
        DialogModel dialog = new DialogModel();

        dialog.addListener(new DialogActionListener() {
            @Override
            public void dialogShown() {}
            @Override
            public void dialogHidden() {}
      
            @Override
            public void dialogAction(int childIdx) {
                dialogActionParam = childIdx;
            }
        });
        assert (dialogActionParam == 0);

        for (int i = Integer.MIN_VALUE/2; i < Integer.MAX_VALUE/2; i++) {
            dialog.invokeAction(i);
            assert (i == dialogActionParam);
        }
    }   
}