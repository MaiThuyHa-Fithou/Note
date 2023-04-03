package com.mtha.mynote;

public class Note {
    private int noteId;
    private String noteTitle;
    private String noteContent;
    private String dateCreate;

    public Note() {
    }

    public Note(int noteId, String noteTitle, String noteContent) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
    }

    public Note(int noteId, String noteTitle, String noteContent, String dateCreate) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.dateCreate = dateCreate;
    }

    public Note(String noteTitle, String noteContent, String dateCreate) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.dateCreate = dateCreate;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    @Override
    public String toString() {
        return noteTitle;
    }
}
