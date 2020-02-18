package et.edu.mu.cfdh;

import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.interceptor.auth.IAuthRule;
import ca.uhn.fhir.rest.server.interceptor.auth.RuleBuilder;

import java.util.List;

public class CustomAuthorizationInterceptor extends ca.uhn.fhir.rest.server.interceptor.auth.AuthorizationInterceptor {
    @Override
    public List<IAuthRule> buildRuleList(RequestDetails theRequestDetails) {

        // Process this header
        String authHeader = theRequestDetails.getHeader("Authorization");

        RuleBuilder builder = new RuleBuilder();
/*        builder
                .allow().metadata().andThen()
                .allow().read().allResources().withAnyId().andThen()
                .allow().write().resourcesOfType(Patient.class).inCompartment("Patient", new IdType()).andThen()
                .allow().write().allResources().inCompartment("Condition", new IdType());
*/
       return  new RuleBuilder()
                .allowAll()
               .build();

//        return builder.build();
    }

}
