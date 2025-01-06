package com.abc.contracts.producer.domains;

import java.util.List;

public record UserInfo(String userId, List<String> preferredGenres) {
}
