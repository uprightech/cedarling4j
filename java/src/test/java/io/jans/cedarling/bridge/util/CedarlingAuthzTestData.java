package io.jans.cedarling.bridge.util;

import io.jans.cedarling.bridge.authz.AuthorizeRequest;
import io.jans.cedarling.bridge.authz.Context;
import io.jans.cedarling.bridge.authz.EntityData;
import io.jans.cedarling.bridge.util.jwt.JwtGenerator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONArray;

public class CedarlingAuthzTestData {
    
    private Map<String,String> tokens = new HashMap<>();
    private String action;
    private Context context;
    private EntityData resource;

    public AuthorizeRequest toAuthorizeRequest() {

        return AuthorizeRequest.builder()
            .tokens(tokens)
            .action(action)
            .resource(resource)
            .context(context)
            .build();
    }

    public static CedarlingAuthzTestData fromResourceFile(final File file,JwtGenerator generator) throws Exception {

        InputStream inputstream = CedarlingAuthzTestData.class.getClassLoader().getResourceAsStream(file.getPath());
        if(inputstream == null) {
            throw new RuntimeException("File not found");
        }
        StringBuilder sb = new StringBuilder();
        byte [] buf = new byte[4096];
        int read = 0;
        while((read = inputstream.read(buf,0,4096)) != -1) {
            sb.append(new String(buf,0,read,"UTF-8"));
        }

        JSONObject obj = new JSONObject(sb.toString());

        CedarlingAuthzTestData data = new CedarlingAuthzTestData();
        JSONArray tokens = obj.getJSONArray("tokens");
        for(int i = 0; i < tokens.length(); i++) {
            JSONObject token_info = tokens.getJSONObject(i);
            String token_type = token_info.getString("type");
            String token_data = generator.generate(token_info.getJSONObject("claims").toString());
            data.tokens.put(token_type,token_data);
        }
        data.action = obj.getString("action");
        data.context = new Context(obj.getJSONObject("context").toString());
        JSONObject resource = obj.getJSONObject("resource");
        String resource_id = resource.getString("id");
        String entity_type = resource.getString("entity_type");
        String resource_attributes = resource.getJSONObject("attributes").toString();
        data.resource = new EntityData(resource_id, entity_type, resource_attributes);
        return data;
    }
}
