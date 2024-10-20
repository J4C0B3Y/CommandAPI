package gg.voided.api.command;

import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.actor.ConsoleActor;
import gg.voided.api.command.actor.PlayerActor;
import gg.voided.api.command.actor.ProxyActor;
import gg.voided.api.command.annotation.parameter.classifier.Label;
import gg.voided.api.command.annotation.parameter.classifier.Sender;
import gg.voided.api.command.annotation.parameter.modifier.Length;
import gg.voided.api.command.annotation.parameter.modifier.Range;
import gg.voided.api.command.annotation.registration.Ignore;
import gg.voided.api.command.annotation.registration.Register;
import gg.voided.api.command.exception.registration.RegistrationException;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.flag.FlagAction;
import gg.voided.api.command.execution.usage.UsageHandler;
import gg.voided.api.command.execution.usage.impl.SimpleUsageHandler;
import gg.voided.api.command.execution.locale.CommandLocale;
import gg.voided.api.command.wrapper.CommandHandle;
import gg.voided.api.command.wrapper.CommandWrapper;
import gg.voided.api.command.wrapper.parameter.binding.BindingBuilder;
import gg.voided.api.command.wrapper.parameter.binding.BindingHandler;
import gg.voided.api.command.wrapper.parameter.modifier.ModifierHandler;
import gg.voided.api.command.wrapper.parameter.modifier.impl.LengthModifier;
import gg.voided.api.command.wrapper.parameter.modifier.impl.RangeModifier;
import gg.voided.api.command.wrapper.parameter.provider.impl.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 26/08/2024
 */
@Getter @Setter
public abstract class CommandHandler {
    private final BindingHandler bindingHandler = new BindingHandler();
    private final ModifierHandler modifierHandler = new ModifierHandler();

    private UsageHandler usageHandler = new SimpleUsageHandler();
    private CommandLocale locale = new CommandLocale();

    private FlagAction unknownFlagAction = FlagAction.ARGUMENT;

    private boolean debug;

    public CommandHandler() {
        initialize();
    }

    public abstract CommandWrapper wrap(Object wrapper, String name, List<String> aliases);
    public abstract Logger getLogger();

    public <T> BindingBuilder<T> bind(Class<T> type) {
        return new BindingBuilder<>(type, this);
    }

    public void register(Object wrapper, String name, String... aliases) {
        if (wrapper.getClass().isAnnotationPresent(Ignore.class)) return;

        try {
            wrap(wrapper, name, Arrays.asList(aliases)).register();
        } catch (Exception exception) {
            throw new RegistrationException("Failed to register command '" + name + "'", exception);
        }
    }

    public void register(Object wrapper) {
        Class<?> clazz = wrapper.getClass();
        Register annotation = clazz.getAnnotation(Register.class);

        if (annotation == null) {
            throw new RegistrationException("Wrapper '" + clazz.getSimpleName() + "' must be annotated @Register.");
        }

        register(wrapper, annotation.name(), annotation.aliases());
    }

    public void runTask(Runnable task, boolean async) {
        if (!async) {
            task.run();
            return;
        }

        CompletableFuture.runAsync(task);
    }

    public void initialize() {
        bind(String.class).to(new StringProvider());
        bind(char.class).to(new CharacterProvider());
        bind(boolean.class).to(new BooleanProvider());

        bind(int.class).to(new NumberProvider<>(Integer::parseInt, "integer", 0));
        bind(double.class).to(new NumberProvider<>(Double::parseDouble, "double", 0D));
        bind(float.class).to(new NumberProvider<>(Float::parseFloat, "float", 0f));
        bind(long.class).to(new NumberProvider<>(Long::parseLong, "long", 0L));
        bind(short.class).to(new NumberProvider<>(Short::parseShort, "short", (short) 0));

        bind(String.class).annotated(Length.class).to(new LengthModifier());
        bind(int.class).annotated(Range.class).to(new RangeModifier<>());
        bind(double.class).annotated(Range.class).to(new RangeModifier<>());
        bind(float.class).annotated(Range.class).to(new RangeModifier<>());
        bind(long.class).annotated(Range.class).to(new RangeModifier<>());

        bind(String.class).annotated(Label.class).to(new LabelProvider());
        bind(Actor.class).annotated(Sender.class).to(new ActorProvider());
        bind(ProxyActor.class).annotated(Sender.class).to(new ProxyActorProvider());
        bind(PlayerActor.class).annotated(Sender.class).to(new PlayerActorProvider());
        bind(ConsoleActor.class).annotated(Sender.class).to(new ConsoleActorProvider());

        bind(CommandExecution.class).to(new CommandExecutionProvider());
        bind(CommandHandle.class).to(new CommandHandleProvider());
        bind(CommandWrapper.class).to(new CommandWrapperProvider());

        bind(CommandHandler.class).to(this);
    }
}
