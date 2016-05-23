class Run < Formula
  desc "Run: Aliases made easy"
  homepage "https://github.com/kennycason/run"
  url "http://search.maven.org/remotecontent?filepath=com/kennycason/run/1.0/run-1.0.jar"
  sha256 "bad03409262be9b6ded7bd1a900209c13feecc415dcedad5b735ce76039f5f32"

  def install
    libexec.install "run-1.0.jar"
    bin.write_jar_script libexec/"run-1.0.jar", "run"
    puts "Finished installing Run 1.0"
  end

  def server_script(server_jar); <<-EOS.undent
    #!/bin/bash
    exec java -cp #{server_jar} com.kennycason.run.RunKt "$@"
  EOS
  end

  test do
    pipe_output("#{bin}/run version", "Test Run version command")
  end
end
