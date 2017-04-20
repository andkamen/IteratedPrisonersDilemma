package com.ipdweb.areas.user.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("facebook_user")
public class FacebookUser extends User {

}
