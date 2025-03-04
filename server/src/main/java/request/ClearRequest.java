package request;

import java.util.Set;

public record ClearRequest(Set<String> header, String body) {
}
