package com.eb.service.models;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.hateoas.Link;

import com.eb.store.models.IdentityProviderMetadata;
import com.eb.store.models.IdentityProviderType;
import com.eb.store.models.Subscription;
import com.eb.store.models.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionEventData {

	public SubscriptionEventData() {
		super();
	}

	private String type;

	@Override
	public String toString() {
		return "SubscriptionEventData [type=" + type + ", marketplace=" + marketplace + ", creator=" + creator
				+ ", flag=" + flag + ", returnUrl=" + returnUrl + ", links=" + links + ", payload=" + payload + "]";
	}

	private MarketPlace marketplace;
	private AppdirectUser creator;
	private String flag;
	private String returnUrl;
	private String applicationUuid;
	private Collection<Link> links;
	public String getApplicationUuid() {
		return applicationUuid;
	}

	public void setApplicationUuid(String applicationUuid) {
		this.applicationUuid = applicationUuid;
	}

	private Payload payload;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MarketPlace getMarketplace() {
		return marketplace;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public Collection<Link> getLinks() {
		return links;
	}

	public void setLinks(Collection<Link> links) {
		this.links = links;
	}

	public String getFlag() {
		return flag;
	}

	public AppdirectUser getCreator() {
		return creator;
	}

	public void setCreator(AppdirectUser creator) {
		this.creator = creator;
	}

	public String isFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	public void setMarketplace(MarketPlace marketPlace) {
		this.marketplace = marketPlace;
	}
	
	public Subscription AsSubscription()
	{
		Subscription subscription = new Subscription();
		subscription.setQuantity(getPayload().getOrder().getItems().get(0).getQuantity());
		User user = new User();
		user.setVendorId(getCreator().getUuid());
		user.setEmail(getCreator().getEmail());
		user.setSubscription(subscription);
		subscription.setOwner(user);
		List<User> users = new LinkedList<>();
		users.add(user);
		subscription.setUsers(users);
		IdentityProviderMetadata metadata = new IdentityProviderMetadata();
		if (getLinks()!=null && !getLinks().isEmpty())
			metadata.setSamlMetadataURI(getLinks().iterator().next().getHref());
		else
			metadata.setSamlMetadataURI("uri");
		metadata.setType(IdentityProviderType.SAML);
		subscription.setIdentityProviderMetadata(metadata);
		return subscription;
	}

}