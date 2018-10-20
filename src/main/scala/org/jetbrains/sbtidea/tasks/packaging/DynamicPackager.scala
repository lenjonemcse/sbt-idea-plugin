package org.jetbrains.sbtidea.tasks.packaging

import java.nio.file.{FileSystem, Files, Path}

import org.jetbrains.sbtidea.tasks.packaging.ExcludeFilter.ExcludeFilter
import sbt.Keys.TaskStreams

class DynamicPackager(myOutput: Path,
                      shader: ClassShader,
                      excludeFilter: ExcludeFilter,
                      incrementalCache: IncrementalCache)
                     (implicit private val streams: TaskStreams)
  extends SimplePackager(myOutput, shader, excludeFilter, incrementalCache) {

  override protected def outputExists(path: Path): Boolean = Files.exists(path)

  override protected def createOutputFS(output: Path): FileSystem = {
    if (!output.toFile.exists())
      Files.createDirectories(output)
    output.getFileSystem
  }

  override protected def createOutput(srcPath: Path, output: Path, outputFS: FileSystem): Path = {
    if (srcPath.toString.contains("META-INF"))
      myOutput.getParent.resolve(srcPath)
    else
      myOutput.resolve(srcPath)
  }

}
