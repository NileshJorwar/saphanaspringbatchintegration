## Spring Batch with SAP HANA ##
# Why SAP Hana and Spring Batch Custom Integration?
We know that spring batch creates the default tables for storing the job information.
It creates the set of 6 default tables for storing batch job information.
But, spring Batch latest dependency does not have support for the SAP Hana
when it comes to store the metadata information.
Hence, custom implementation is needed for handling metadata information.
I have created create/drop table scripts to store metadata information. 
These tables have to create once in lifetime hence need to set 
`saphana.run = false` when ran initially `saphana.run = true`

# Build Deploy and Run
1. Make sure to change the application.yml file for your SAP HANA datasource changes.
2. Adjust the `saphana.run = false` or `saphana.run = false` as needed.
3. Run the application.
