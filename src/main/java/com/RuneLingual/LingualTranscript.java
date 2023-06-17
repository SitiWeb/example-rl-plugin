package com.RuneLingual;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LingualTranscript implements Serializable{
    private Map<String, Map<String, String>> transcript = new HashMap<>();
    private static final long serialVersionUID = 9190132562154153951L;

    public void addTranscript(String name, String text) throws Exception {
        /*** Only useful to master
         //
         // if found transcript does not exist within the database
         // adds a new entry to commit later***/
        String nameKey = name.replaceAll("[\\s\\p{Punct}]", "").toLowerCase();
        String textKey = text.replaceAll("[\\s\\p{Punct}]", "").toLowerCase();

        Map<String, String> npcTranscripts = transcript.get(nameKey);
        if (npcTranscripts == null) {
            npcTranscripts = new HashMap<>();
            npcTranscripts.put("name", name);
            transcript.put(nameKey, npcTranscripts);
        }
        npcTranscripts.put(textKey, text);
    }

    public String getTranslatedText(String name, String text) throws Exception {

        // ensures valid access to local npc transcript database
        if (transcript == null) {throw new Exception("transcript instance is null");}

        // polishes arguments to get transcript keys
        String nameKey = name.replaceAll("[\\s\\p{Punct}]", "").toLowerCase();
        String textKey = text.replaceAll("[\\s\\p{Punct}]", "").toLowerCase();

        if(transcript.containsKey(nameKey)){
            // retrieves all dialog lines from any given NPC
            Map<String, String> npcTranscripts = transcript.get(nameKey);
            if(npcTranscripts.containsKey(textKey)){
                // retrieves a specific dialog line from the previously accessed NPC
                return npcTranscripts.get(textKey);
            }
            // if given text line was not found - adds it then returns the updated text
            this.addTranscript(name, text);
            return getTranslatedText(name, text);
        }
        // if given npc was not found on the database - adds it then returns the updated text
        this.addTranscript(name, text);
        return getTranslatedText(name, text);

    }
    public String getTranslatedName(String name) throws Exception {

        // ensures valid access to local npc transcript
        if (transcript == null) {throw new Exception("transcript instance is null");}

        // polishes arguments to get transcript keys
        String nameKey = name.strip();

        if(transcript.containsKey(nameKey)){
            // retrieves all dialog lines from any given NPC
            Map<String, String> npcTranscripts = transcript.get(nameKey);

            // retrieves translated npc name
            return npcTranscripts.get("name");

        }
        // if translated name was not found
        return name;
    }
}