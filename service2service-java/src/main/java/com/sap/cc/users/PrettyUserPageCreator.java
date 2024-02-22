package com.sap.cc.users;

import com.sap.cc.ascii.AsciiArtRequest;
import com.sap.cc.ascii.AsciiArtServiceClient;
import org.springframework.stereotype.Component;

@Component
public class PrettyUserPageCreator {

    private AsciiArtServiceClient asciiArtServiceClient;
    public PrettyUserPageCreator(AsciiArtServiceClient asciiArtServiceClient) {
        this.asciiArtServiceClient = asciiArtServiceClient;
    }

    public String getPrettyPage(User user) {
        // Create new AsciiArtRequest for user's name
        AsciiArtRequest asciiArtRequest = new AsciiArtRequest(user.getName(), user.getFontPreference());

        // Retrieve the ascii art using getAsciiString(AsciiArtRequest)
        String asciiArt = this.asciiArtServiceClient.getAsciiString(asciiArtRequest);

        // Return the ascii art (instead of user's name) along with user's phonenumber
        return asciiArt + "\r\n" + user.getPhoneNumber();
    }
}