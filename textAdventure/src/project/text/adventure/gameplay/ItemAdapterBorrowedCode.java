package project.text.adventure.gameplay;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * * Borrowed code which puts a label on all items. When the program reads from the gson-file it will understand which type
 * of item it is.
 **/
public final class ItemAdapterBorrowedCode<T> implements JsonSerializer<T>, JsonDeserializer<T>
{
    public JsonElement serialize(T object, Type interfaceType, JsonSerializationContext context) {
        final JsonObject wrapper = new JsonObject();
        wrapper.addProperty("type", object.getClass().getName());
        wrapper.add("data", context.serialize(object));
        return wrapper;
    }

    public T deserialize(JsonElement element, Type interfaceType, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject wrapper = (JsonObject) element;
        final JsonElement typeName = get(wrapper, "type");
        final Type actualType = getTypeForName(typeName);
        final JsonElement data = get(wrapper, "data");
        return context.deserialize(data, actualType);
    }

    private Type getTypeForName(final JsonElement typeElement) {
        try {
            return Class.forName(typeElement.getAsString());
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    private JsonElement get(final JsonObject wrapper, String memberName) {
        final JsonElement element = wrapper.get(memberName);
        if (element == null) throw new JsonParseException("no '" + memberName + "' member found in what was expected to be an interface wrapper");
        return element;
    }
}

