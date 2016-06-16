package io.jenkins.blueocean.service.embedded.rest;

import io.jenkins.blueocean.commons.ServiceException;
import io.jenkins.blueocean.commons.stapler.JsonBody;
import io.jenkins.blueocean.rest.ApiHead;
import io.jenkins.blueocean.rest.hal.Link;
import io.jenkins.blueocean.rest.model.BlueOrganization;
import io.jenkins.blueocean.rest.model.BluePipelineContainer;
import io.jenkins.blueocean.rest.model.BlueUserContainer;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.WebMethod;
import org.kohsuke.stapler.verb.DELETE;
import org.kohsuke.stapler.verb.PUT;

import java.io.IOException;

/**
 * {@link BlueOrganization} implementation for the embedded use.
 *
 * @author Vivek Pandey
 * @author Kohsuke Kawaguchi
 */
public class OrganizationImpl extends BlueOrganization {
    private final UserContainerImpl users = new UserContainerImpl(this);

    /**
     * In embedded mode, there's only one organization
     */
    public static final OrganizationImpl INSTANCE = new OrganizationImpl();

    private OrganizationImpl() {
    }

    /**
     * In embedded mode, there's only one organization
     */
    public String getName() {
        return Jenkins.getInstance().getDisplayName().toLowerCase();
    }

    @Override
    public BluePipelineContainer getPipelines() {
        return new PipelineContainerImpl(Jenkins.getInstance(), getLink());
    }

    @WebMethod(name="") @DELETE
    public void delete() {
        throw new ServiceException.NotImplementedException("Not implemented yet");
    }

    @WebMethod(name="") @PUT
    public void update(@JsonBody OrganizationImpl given) throws IOException {
        given.validate();
        throw new ServiceException.NotImplementedException("Not implemented yet");
//        getXmlFile().write(given);
    }

    private void validate() {
//        if (name.length()<2)
//            throw new IllegalArgumentException("Invalid name: "+name);
    }

    /**
     * In the embedded case, there's only one organization and everyone belongs there,
     * so we can just return that singleton.
     */
    @Override
    public BlueUserContainer getUsers() {
        return users;
    }

    @Override
    public Link getLink() {
        return ApiHead.INSTANCE().getLink().rel("organizations/"+getName());
    }

}
