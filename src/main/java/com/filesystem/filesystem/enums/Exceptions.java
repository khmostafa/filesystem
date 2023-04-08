package com.filesystem.filesystem.enums;

public enum Exceptions {

    File_Id_Is_Null,
    File_Name_Is_Null,
    File_Type_Is_Null,
    File_ParentFileId_Is_Null,
    File_GroupId_Is_Null,
    File_Data_Is_Null,

    Requester_Email_Is_Null,

    Operation_Is_Denied,
    Only_File_Type_That_Can_Have_BinaryData,
    File_Type_Value_Is_Not_Valid,
    File_Cannot_Be_Downloaded_FileType_Value_Is_Not_File,
    File_ParentFile_Is_Not_Exist,
    File_ParentFile_Type_Is_Not_Valid,
    File_Group_Is_Not_Exist,
    Permission_Id_Is_Null,
    Permission_level_Is_Null,
    Permission_level_Value_Is_Not_Valid,
    Permission_UserEmail_Is_Null,
    Permission_GroupId_Is_Null,
    Permission_Group_Is_Not_Existed,
    Item_Already_Existed,
    Item_Deleted_Successfully,
    Item_Updated_Successfully,
    Item_Does_Not_Exist;
}
