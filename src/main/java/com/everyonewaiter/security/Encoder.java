package com.everyonewaiter.security;

interface Encoder<E extends Encodable, D extends Decodable> {

	String encode(E encodable);

	boolean matches(E encodable, D decodable);
}
