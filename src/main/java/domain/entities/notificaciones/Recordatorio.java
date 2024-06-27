package domain.entities.notificaciones;

import domain.entities.notificaciones.adapters.AdapterEmail;
import domain.entities.notificaciones.adapters.AdapterWhatsapp;
import domain.entities.actores.organizaciones.Contacto;
import lombok.Setter;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;

@Setter
public class Recordatorio {
    private List<Contacto> contactos;
    private AdapterEmail adapterEmail;
    private AdapterWhatsapp adapterWhatsapp;
    private String cuerpoMensaje;
    private Scheduler scheduler;
    private JobDetail job;

    public void iniciarRecordatorio(ConfiguracionCron configuracionCron) {
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            job = JobBuilder.newJob(Notificacion.class)
                    .withIdentity("job", "group1").build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule(configuracionCron.getCronExpresion()))
                    .build();

            scheduler.scheduleJob(job, trigger);
            scheduler.start();

            Thread.sleep(100000);
            scheduler.shutdown();
        } catch (SchedulerException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarRecordatorio() {
        try {
            scheduler.deleteJob(job.getKey());
            scheduler.shutdown(true);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public class Notificacion implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            adapterEmail.enviarNotificacion(cuerpoMensaje, contactos);
            adapterWhatsapp.enviarNotificacion(cuerpoMensaje, contactos);
        }
    }
}
