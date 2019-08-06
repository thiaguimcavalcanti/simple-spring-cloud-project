package com.bot.exchanges.commons.entities;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;

/**
 * @author Vlad Mihalcea
 */
public class GenerateUUIDIdentifier implements IdentifierGenerator, Configurable {

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {

	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj) {
		if (obj instanceof AbstractStringIDEntity) {
			AbstractStringIDEntity identifiable = (AbstractStringIDEntity) obj;
			Serializable id = identifiable.getId();
			if (id != null) {
				return id;
			}
		}

		return UUID.randomUUID().toString();
	}
}