package biz.gelicon.core.view;

import biz.gelicon.core.components.erp.todo.sgood.Sgood;

public class TreeViewSgoodItem extends TreeViewItem{

    public TreeViewSgoodItem(int sgoodId, String name, int parentId) {
        super(sgoodId,name,parentId);
    }

    public static TreeViewSgoodItem root() {
        return new TreeViewSgoodItem(Sgood.SGOOD_ROOT_ID,"Все ТМЦ",Sgood.SGOOD_ROOT_ID);
    }

}
