/**
Place this file in the src directory of your Gaelyk project to enable the Employee and PayPeriod domain classes.

**/

class DomainDefinition {
    static def list() {
        [
            //This entity contains two simple String attributes and a customized attribute
            new DomainEntity("Employee",  ["firstName","lastName",
                                            new Attribute(name:"ssn",
                                                          transformToString:{it?"${it[0..2]}-${it[3..4]}-${it[5..8]}":""},
                                                          transformFromString:{it.replaceAll('-', '')})
                                          ], 
            //Validate that we have a first and last name                                                            
            {params ->
                return params.firstName && params.lastName
            }),
            //This entity uses an extension of Attribute to do the transformations to and from request parameters/attributes
            new DomainEntity("PayPeriod", 
                    [
                        new DateAttribute(name:"startDate"),
                        new DateAttribute(name:"endDate")
                    ])
        ]
    }
}
