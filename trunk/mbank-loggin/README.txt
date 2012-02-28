To correct runing this application you need to have configured JMS Queue in your server. If you uase Jboss AS 6, this server has builded jms provider HornetQ, you can add new jms Queue with name testQueue in file JBOSS_HOME/server/<name>/deploy/hornetq/hornetq-jms.xml. Details: in the end of xml are tags queue, near this tags add your one

<queue name="testQueue">
    <entry name="/queue/testQueue"/>
</queue>

after restart server.