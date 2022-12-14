package biz.gelicon.core.utils;

public class ConstantForControllers {

    public static final String GETLIST_OPERATION_SUMMARY = "Получение списка";
    public static final String GETLIST_OPERATION_DESCRIPTION = "Возвращает постраничный список";
    public static final String GETLIST_FULL_OPERATION_DESCRIPTION = "Возвращает полный список";
    public static final String GET_OPERATION_SUMMARY = "Получение данных по идентификатору";
    public static final String GET_OPERATION_DESCRIPTION =
            "Возвращает один объект по идентификатору, если он указан. "
                    + "Если идентификатор отсутствует - возвращается объект с пустым идентификатором "
                    + "и полями, заполненными значениями по умолчанию";
    public static final String SAVE_OPERATION_SUMMARY = "Сохранение (добавление или изменение)";
    public static final String SAVE_OPERATION_DESCRIPTION = "Сохраняет объект. "
            + "Объект с пустым идентификатором добавляется, с непустым - изменяется";
    public static final String DELETE_OPERATION_SUMMARY = "Удаление одного или нескольких";
    public static final String DELETE_OPERATION_DESCRIPTION = "Удаляет один или несколько объектов. "
            + "Идентификаторы удаляемых объектов перечисленны через запятую.";

}
