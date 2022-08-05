package biz.gelicon.core.components.core.subject;

import biz.gelicon.core.view.TreeViewItem;

public class TreeViewSubjectItem extends TreeViewItem {

    public TreeViewSubjectItem(int subjecId, String name, int parentId) {
        super(subjecId,name,parentId);
    }

    public static TreeViewSubjectItem root() {
        return new TreeViewSubjectItem(Subject.SUBJECT_ROOT_ID,"Объекты аналитического учета",Subject.SUBJECT_ROOT_ID);
    }

}
