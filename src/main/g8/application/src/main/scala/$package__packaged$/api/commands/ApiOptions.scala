package $package$.api.commands

import cats.implicits._
import com.monovore.decline.Opts
import eu.timepit.refined.types.numeric.PosInt
import com.monovore.decline.refined._

trait ApiOptions {

  private val externalPortHelp = "Port users/clients hit for requests"
  private val externalPort = (Opts
    .option[PosInt]("external-port", help = externalPortHelp)
    .orElse(Opts.env[PosInt]("API_EXTERNAL_PORT", help = externalPortHelp)))
    .withDefault(PosInt($default_api_port$))

  private val internalPortHelp =
    "Port server listens on, this will be different from 'external-port' when service is started behind a proxy"
  private val internalPort = (Opts
    .option[PosInt]("internal-port", help = internalPortHelp)
    .orElse(Opts.env[PosInt]("API_INTERNAL_PORT", help = internalPortHelp)))
    .withDefault(PosInt($default_api_port$))

  private val apiHostHelp = "Hostname $name$ is hosted it (e.g. localhost)"
  private val apiHost = (Opts
    .option[String]("api-host", help = apiHostHelp)
    .orElse(Opts.env[String]("API_HOST", help = apiHostHelp)))
    .withDefault("localhost")

  private val apiSchemeHelp = "Scheme server is exposed to end users with"
  private val apiScheme =
    (Opts
      .option[String]("api-scheme", help = apiSchemeHelp)
      .orElse(Opts.env[String]("API_SCHEME", help = apiSchemeHelp)))
      .withDefault("http")
      .validate("Scheme must be either 'http' or 'https'")(s =>
        (s == "http" || s == "https")
      )

  val apiConfig: Opts[ApiConfig] =
    (externalPort, internalPort, apiHost, apiScheme) mapN ApiConfig
}
